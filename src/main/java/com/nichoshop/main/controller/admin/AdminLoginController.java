package com.nichoshop.main.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.duosecurity.Client;
import com.duosecurity.exception.DuoException;
import com.duosecurity.model.Token;

import com.nichoshop.main.exception.CustomException;
import com.nichoshop.main.service.UserService;
import com.nichoshop.main.service.SUCService;
import com.nichoshop.main.util.CaptchaClient;
import com.nichoshop.main.util.Environment;
import com.nichoshop.main.util.Environment.duo;

import lombok.RequiredArgsConstructor;

import com.nichoshop.main.service.DuoConfirmService;
import com.nichoshop.main.service.RoleService;
import com.nichoshop.main.model.DuoConfirm;
import com.nichoshop.main.model.Role;
import com.nichoshop.main.model.User;
import com.nichoshop.main.request.AdminLoginRequest;
import com.nichoshop.main.response.BasicResponse;
import com.nichoshop.main.response.DuoSucessReponse;
import com.nichoshop.main.security.JwtTokenProvider;
import com.nichoshop.main.security.RoleEum;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/login")
public class AdminLoginController {
    private final Logger log = LoggerFactory.getLogger(getClass());
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

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private Client duoClient;

    @PostConstruct
    public void initializeDuoClient() throws DuoException {
        duoClient = new Client.Builder(duo.clientId, duo.clientSecret, duo.apiHost, duo.redirectUri).build();
    }

    @PostMapping("")
    public ResponseEntity<?> AdminLogin(@RequestBody AdminLoginRequest adminLoginRequest)
            throws AuthenticationException, DuoException, IOException {

        String login = adminLoginRequest.getLogin();
        String password = adminLoginRequest.getPassword();
        String recaptcha = adminLoginRequest.getRecaptcha();

        try {

            Boolean captcha = Environment.skipCaptcha || CaptchaClient.checkCaptchaV2(recaptcha);
            if (captcha) {

                User user = userService.getUserByUsernameOrEmail(login);

                // check if the user exists.
                if (user != null) {

                    // check the password.
                    if (BCrypt.checkpw(user.getPassword(), password)) {

                        // check if the user has admin or customer service role.
                        if (userService.hasAdminRole(user.getId())) {

                            try {
                                duoClient.healthCheck();
                            } catch (DuoException exception) {
                                log.info("Duo exception:" + exception);
                            }

                            String state = duoClient.generateState();

                            DuoConfirm duoConfirm = new DuoConfirm();
                            duoConfirm.setState(state);
                            duoConfirm.setUsername(user.getUsername());
                            duoConfirmService.saveOrUpdate(duoConfirm);

                            String authUrl = duoClient.createAuthUrl(user.getUsername(), state);

                            DuoSucessReponse duoSucessReponse = new DuoSucessReponse();
                            duoSucessReponse.setData(authUrl);
                            return new ResponseEntity<>(duoSucessReponse, HttpStatus.OK);

                        } else
                            throw new CustomException("ALLOW_LOGIN_ONLY_ADMIN_OR_CUSTOMER_SUPPORT",
                                    HttpStatus.BAD_REQUEST);
                    } else
                        throw new CustomException("Password invalid.", HttpStatus.BAD_REQUEST);

                } else
                    throw new CustomException("User not found", HttpStatus.BAD_REQUEST);

            } else
                throw new CustomException("Captcha failed.", HttpStatus.BAD_REQUEST);

        } catch (AuthenticationException ae) {
            throw new CustomException("ALLOW_LOGIN_ONLY_ADMIN_OR_CUSTOMER_SUPPORT", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/confirm")

    public ResponseEntity<?> confirmAdminAccount(@RequestParam("state") String state,
            @RequestParam("duo_code") String duoCode) throws DuoException, UnsupportedEncodingException {

        DuoConfirm duoConfirm = duoConfirmService.getDuoByState(state);

        // Verify DUO 2FA
        Token token = duoClient.exchangeAuthorizationCodeFor2FAResult(duoCode, duoConfirm.getUsername());
        if (authWasSuccessful(token)) {

            User user = userService.getUserByUsernameOrEmail(duoConfirm.getUsername());

            if (user != null) {

                // Delete State from Database
                duoConfirmService.deleteById(duoConfirm.getId());

                String user_token = "";

                // // Create a session if no exist.
                try {
                    Role roleEntity = roleService.getRoleByUserId(user.getId());
                    List<RoleEum> role = new ArrayList<RoleEum>();

                    if (roleEntity.getType().equals(RoleEum.ROLE_ADMIN.getAuthority())) {

                        role.add(RoleEum.ROLE_ADMIN);
                    } else if (roleEntity.getType().equals(RoleEum.ROLE_USER.getAuthority())) {

                        role.add(RoleEum.ROLE_USER);
                    } else if (roleEntity.getType().equals(RoleEum.ROLE_CS.getAuthority())) {

                        role.add(RoleEum.ROLE_CS);
                    }
                    authenticationManager
                            .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                                    user.getPassword()));
                    user_token = jwtTokenProvider.createToken(user.getUsername(),
                            role);
                } catch (AuthenticationException e) {
                    throw new CustomException("Invalid username/password supplied",
                            HttpStatus.UNPROCESSABLE_ENTITY);
                }

                return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                        .header("location", "http://localhost:3000/admin?login=true&data=" + user_token)
                        .body("Redirect");
            } else
                throw new CustomException("User not found", HttpStatus.BAD_REQUEST);

        } else
            throw new CustomException("Duo check failed.", HttpStatus.BAD_REQUEST);
    }

    private Boolean authWasSuccessful(Token token) {
        if (token != null && token.getAuth_result() != null) {
            return "ALLOW".equalsIgnoreCase(token.getAuth_result().getStatus());
        }
        return false;
    }

}
