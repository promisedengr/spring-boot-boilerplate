package com.nichoshop.main.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.duosecurity.Client;
import com.duosecurity.exception.DuoException;
import com.duosecurity.model.Token;
import com.nichoshop.main.util.Constants;
import com.nichoshop.main.util.Environment;
import com.nichoshop.main.util.SecureUtils;
import com.nichoshop.main.util.Twilio;
import com.nichoshop.main.util.Environment.duo;

import lombok.RequiredArgsConstructor;

import com.nichoshop.main.exception.CustomException;
import com.nichoshop.main.model.DuoConfirm;
import com.nichoshop.main.model.Role;
import com.nichoshop.main.model.SUC;
import com.nichoshop.main.model.User;
import com.nichoshop.main.request.AdminLoginRequest;
import com.nichoshop.main.request.TemporaryPasswordConfirmRequest;
import com.nichoshop.main.response.DuoSucessReponse;
import com.nichoshop.main.security.JwtTokenProvider;
import com.nichoshop.main.security.RoleEum;
import com.nichoshop.main.service.DuoConfirmService;
import com.nichoshop.main.service.RoleService;
import com.nichoshop.main.service.SUCService;
import com.nichoshop.main.service.UserService;

@RestController
@RequestMapping(path = "/admin/login-temp")
@RequiredArgsConstructor
public class AdminLoginTempController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    UserService userService;
    @Autowired
    DuoConfirmService duoConfirmService;
    @Autowired
    SUCService sucService;
    @Autowired
    RoleService roleService;

    private Client duoClient;
    private final JwtTokenProvider jwtTokenProvider;

    // constructor
    @PostConstruct
    public void initializeDuoClient() throws DuoException {
        duoClient = new Client.Builder(duo.clientId, duo.clientSecret, duo.apiHost, duo.redirectUri).build();
    }

    // auth checker for duo token
    private Boolean authWasSuccessful(Token token) {
        return token != null && token.getAuth_result() != null
                && "ALLOW".equalsIgnoreCase(token.getAuth_result().getStatus());
    }

    @PostMapping("")
    public ResponseEntity<?> DuoRequest(@RequestBody AdminLoginRequest adminLoginRequest) throws DuoException {

        // check the duo device health.
        duoClient.healthCheck();

        // get admin id from config.
        String userName = Environment.adminId;

        // generate duo state.
        String state = duoClient.generateState();

        // Save to database
        DuoConfirm duoConfirm = new DuoConfirm();
        duoConfirm.setUsername(userName);
        duoConfirm.setState(state);

        duoConfirmService.saveOrUpdate(duoConfirm);

        // Response
        DuoSucessReponse duoSucessReponse = new DuoSucessReponse();
        duoSucessReponse.setData(duoClient.createAuthUrl(userName, state));

        return new ResponseEntity<>(duoSucessReponse, HttpStatus.OK);
    }

    @GetMapping("request")
    public ResponseEntity<?> TemporaryPasswordRequest(@RequestParam("state") String state,
            @RequestParam("duo_code") String duoCode)
            throws DuoException {

        // get duo confirm data from database.
        DuoConfirm duoConfirm = duoConfirmService.getDuoByState(state);

        // if the data exists.
        if (duoConfirm != null) {

            // get token from duocode and username
            Token token = duoClient.exchangeAuthorizationCodeFor2FAResult(duoCode, duoConfirm.getUsername());

            // if token is verified
            if (authWasSuccessful(token)) {

                // get user by username.
                User user = userService.getUserByUsernameOrEmail(duoConfirm.getUsername());

                if (user != null) {
                    // delete state from database
                    duoConfirmService.deleteById(duoConfirm.getId());

                    // get sucs used by this user.
                    List<String> usedSUCList = sucService.getSUCsByUserId(user.getId());

                    // generate random suc string
                    String sucString = SecureUtils.generateSUC(usedSUCList);

                    // generate database entity.
                    SUC suc = new SUC();
                    suc.setUserId(user.getId());
                    suc.setCode(sucString);
                    suc.setSucType(Constants.sucType.adminTemp);

                    sucService.saveOrUpdate(suc);

                    // send twilio message.
                    new Twilio().sendSMSToNumber(user.getPhone(), sucString);

                    // response.
                    return new ResponseEntity<>("", HttpStatus.OK);

                } else
                    throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
            } else
                throw new CustomException("Duo Auth failed.", HttpStatus.BAD_REQUEST);
        } else
            throw new CustomException("Duo status not exists", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/request/confirm")
    public ResponseEntity<?> DuoRequestConfirm(
            @RequestBody TemporaryPasswordConfirmRequest temporaryPasswordConfirmRequest) {

        // get user by email or username
        User user = userService.getUserByUsernameOrEmail(Environment.adminId);

        // check if the user exists.
        if (user != null) {

            // get the latest suc used by this user with action type.
            SUC suc = sucService.findLastSUC(user.getId(), Constants.sucType.adminTemp);

            // check if the suc exists.
            if (suc != null) {

                // check if the suc matches to request
                if (suc.getCode() == temporaryPasswordConfirmRequest.getSuc()) {

                    // caculate the expired time.
                    Long duration = System.currentTimeMillis() - suc.getCreatedAt().getTime();

                    // check if expires.
                    if (duration <= Constants.sucExpire.adminTemp) {
                        String token = "";
                        // Create a user context and token.
                        try {
                            Role roleEntity = roleService.getRoleById(user.getId());
                            List<RoleEum> role = new ArrayList<RoleEum>();

                            if (roleEntity.getType().equals(RoleEum.ROLE_ADMIN.getAuthority())) {

                                role.add(RoleEum.ROLE_ADMIN);
                            } else if (roleEntity.getType().equals(RoleEum.ROLE_USER.getAuthority())) {

                                role.add(RoleEum.ROLE_USER);
                            } else if (roleEntity.getType().equals(RoleEum.ROLE_CS.getAuthority())) {

                                role.add(RoleEum.ROLE_CS);
                            }

                            token = jwtTokenProvider.createToken(user.getUsername(),
                                    role);
                        } catch (AuthenticationException e) {
                            throw new CustomException("Invalid username/password supplied",
                                    HttpStatus.UNPROCESSABLE_ENTITY);
                        }

                        // response
                        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                                .header("location", "http://localhost:3000/admin?login=true&data=" + token)
                                .body("Redirect");
                    } else
                        throw new CustomException("SUC expired.", HttpStatus.BAD_REQUEST);
                } else
                    throw new CustomException("SUC not matched.", HttpStatus.BAD_REQUEST);
            } else
                throw new CustomException("SUC not exists.", HttpStatus.BAD_REQUEST);
        } else
            throw new CustomException("User not exists.", HttpStatus.BAD_REQUEST);
    }

}
