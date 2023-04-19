package com.nichoshop.main.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.mail.EmailException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import com.nichoshop.main.util.CaptchaClient;
import com.nichoshop.main.util.Constants;
import com.nichoshop.main.request.LoginRequest;
import com.nichoshop.main.request.ChangePasswordRequest;
import com.nichoshop.main.response.BasicResponse;
import com.nichoshop.main.response.LoginResponse;
import com.nichoshop.main.security.JwtTokenProvider;
import com.nichoshop.main.security.RoleEum;
import com.nichoshop.main.service.UserService;
import com.nichoshop.main.service.RoleService;
import com.nichoshop.main.service.SUCService;
import com.nichoshop.main.util.Emailer;
import com.nichoshop.main.util.Environment;
import com.nichoshop.main.util.SecureUtils;
import com.nichoshop.main.util.Twilio;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

import com.nichoshop.main.model.User;
import com.nichoshop.main.exception.CustomException;
import com.nichoshop.main.model.Role;
import com.nichoshop.main.model.SUC;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RestController
@CrossOrigin
@RequestMapping(path = "/login")
@RequiredArgsConstructor
public class LoginController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    UserService userService;
    @Autowired
    SUCService sucService;
    @Autowired
    RoleService roleService;

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response)
            throws IOException {
        LoginResponse loginResponse = new LoginResponse();

        // check recaptcha.
        Boolean captcha = Environment.skipCaptcha || CaptchaClient.checkCaptchaV2(loginRequest.getGrecaptcha());
        if (captcha) {

            // get user from db
            User user = userService.getUserByUsernameOrEmail(loginRequest.getLogin());
            if (user != null) {

                // auth
                String token = "";
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
                                    loginRequest.getPassword()));
                    token = jwtTokenProvider.createToken(user.getUsername(),
                            role);
                } catch (AuthenticationException e) {
                    throw new CustomException("Invalid username/password supplied",
                            HttpStatus.UNPROCESSABLE_ENTITY);
                }
                loginResponse.setResult("Login Success!");
                loginResponse.setEmail(user.getEmail());
                loginResponse.setUsername(user.getUsername());
                loginResponse.setToken(token);

                return new ResponseEntity<>(loginResponse, HttpStatus.OK);

            } else {
                throw new CustomException("Invalid username/password supplied",
                        HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } else
            throw new CustomException("Recaptcha failed", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add-phone")
    public ResponseEntity<?> AddPhone(@RequestParam("phone") String phoneNum, Authentication auth) {
        // get user by auth
        User user = userService.getUserByUsernameOrEmail(auth.getName());

        // check if not null.
        if (user != null) {

            // check if phone number is valid.
            if (SecureUtils.isValidPhoneNumber(phoneNum)) {

                // get suc list used by this user.
                List<String> usedSUCList = sucService.getSUCsByUserId(user.getId());

                // get random suc code.
                String code = SecureUtils.generateSUC(usedSUCList);

                // save suc to database.
                SUC suc = new SUC();
                suc.setUserId(user.getId());
                suc.setCode(code);
                suc.setSucType(Constants.sucType.addPhone);
                sucService.saveOrUpdate(suc);

                // update phone number but not verified.
                userService.updateUserPhoneNum(user.getId(), phoneNum);

                // send suc to twilio.
                Twilio twilio = new Twilio();
                twilio.sendSMSToNumber(phoneNum, code);

                // response.
                BasicResponse basicResponse = new BasicResponse();
                basicResponse.setMessage("Phone number is updated.");
                return new ResponseEntity<>(basicResponse, HttpStatus.OK);

            } else
                throw new CustomException("Invalid phone number.", HttpStatus.BAD_REQUEST);
        } else
            throw new CustomException("Invalid User.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/send-suc")
    public ResponseEntity<?> sendSUCByType(@RequestParam("suc_type") Integer sucType, Authentication authentication) {

        // get user from authentication
        User user = userService.getUserByUsernameOrEmail(authentication.getName());

        // check if the user exists.
        if (user != null) {

            // get used suc list of this user.
            List<String> usedSUCList = sucService.getSUCsByUserId(user.getId());

            // generate random suc code.
            String code = SecureUtils.generateSUC(usedSUCList);

            // save to database.
            SUC suc = new SUC();
            suc.setUserId(user.getId());
            suc.setCode(code);
            suc.setSucType(sucType);
            sucService.saveOrUpdate(suc);

            // check if should send by Email or Twilio.
            if (sucType == Constants.sucType.forgotEmail || sucType == Constants.sucType.tempEmail
                    || sucType == Constants.sucType.adminTemp) {
                try {
                    Emailer.sendSUCConfirmation(user, code);
                } catch (EmailException e) {
                    throw new CustomException("Email not send.", HttpStatus.BAD_REQUEST);
                }
            } else {
                new Twilio().sendSMSToNumber(user.getPhone(), code);
            }

            // response.
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("SUC is sent");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/confirm-suc")
    public ResponseEntity<?> confirmSUC(@RequestParam("suc") String code, @RequestParam("suc_type") Integer sucType,
            Authentication authentication) throws UnsupportedEncodingException {

        // get user from auth.
        User user = userService.getUserByUsernameOrEmail(authentication.getName());

        // check if not null.
        if (user != null) {

            // get the latest suc used by this user with action type.
            SUC suc = sucService.findLastSUC(user.getId(), sucType);

            // check if equal to data from customer.
            if (code == suc.getCode()) {

                // calculate the expired time.
                Long duration = System.currentTimeMillis() - suc.getCreatedAt().getTime();

                // check which type of action used
                if (sucType == Constants.sucType.forgotEmail || sucType == Constants.sucType.forgotText) {

                    if (duration <= Constants.sucExpire.forgotPass) {

                        Map<String, Object> userData = new HashMap<String, Object>();
                        userData.put("id", user.getId().toString());
                        userData.put("username", user.getUsername());
                        userData.put("email", user.getEmail());

                        String changePasswordToken = SecureUtils.generateJWTToken(Constants.tokenSecret,
                                Constants.tokenExpire, userData);

                        // response
                        BasicResponse basicResponse = new BasicResponse();
                        basicResponse.setMessage(changePasswordToken);
                        return new ResponseEntity<BasicResponse>(basicResponse, HttpStatus.OK);

                    } else
                        throw new CustomException("Suc expired.", HttpStatus.BAD_REQUEST);

                } else if (sucType == Constants.sucType.tempText || sucType == Constants.sucType.tempEmail) {
                    if (duration <= Constants.sucExpire.tempPass) {
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
                            String token = jwtTokenProvider.createToken(user.getUsername(),
                                    role);
                            // response.
                            LoginResponse loginResponse = new LoginResponse();
                            loginResponse.setResult("Login Success!");
                            loginResponse.setEmail(user.getEmail());
                            loginResponse.setUsername(user.getUsername());
                            loginResponse.setToken(token);

                            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
                        } catch (AuthenticationException e) {
                            throw new CustomException("Invalid username/password supplied",
                                    HttpStatus.UNPROCESSABLE_ENTITY);
                        }
                    } else
                        throw new CustomException("SUC expired", HttpStatus.BAD_REQUEST);
                } else if (sucType == Constants.sucType.addPhone) {
                    if (duration <= Constants.sucExpire.addPhone) {
                        // Confirm passsword
                        userService.confirmPhone(user.getId());

                        BasicResponse basicResponse = new BasicResponse();
                        basicResponse.setMessage("Phone verified.");
                        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
                    } else
                        throw new CustomException("SUC expired", HttpStatus.BAD_REQUEST);
                }
            } else
                throw new CustomException("Invalid SUC.", HttpStatus.BAD_REQUEST);

        }
        throw new CustomException("SUC not exists.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/check-user")
    public ResponseEntity<?> CheckUser(@RequestParam("user") String userString,
            @RequestParam("greptcha") String grecaptcha) throws IOException {

        // check recaptcha
        Boolean captcha = Environment.skipCaptcha || CaptchaClient.checkCaptchaV2(grecaptcha);
        if (captcha) {
            User user = userService.getUserByUsernameOrEmail(userString);
            if (user != null) {
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setResult("User confirmed.");
                loginResponse.setEmail(user.getEmail());
                loginResponse.setUsername(user.getUsername());

                return new ResponseEntity<>(loginResponse, HttpStatus.OK);

            } else
                throw new CustomException("Email or Username not exists.", HttpStatus.BAD_REQUEST);
        } else
            throw new CustomException("Captcha failed.", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException,
            IllegalArgumentException, UnsupportedEncodingException {

        // get data from jwt.
        Map<String, Object> claim = SecureUtils.verifyJWTToken(Constants.tokenSecret, changePasswordRequest.getToken());

        // check if the username property exists.
        if (claim.get("username").equals(changePasswordRequest.getUsername())) {
            if (changePasswordRequest.getPassword() != "" && changePasswordRequest.getConfirm() != "") {
                if (changePasswordRequest.getPassword().equals(changePasswordRequest.getConfirm())) {

                    // change password.
                    userService.changePassword(changePasswordRequest.getUsername(),
                            changePasswordRequest.getPassword());

                    // response
                    BasicResponse basicResponse = new BasicResponse();
                    basicResponse.setMessage("Password changed.");
                    return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                            .header("location", "http://localhost:3000/admin/signin")
                            .body("Redirect");
                } else
                    throw new CustomException("Password confirmation failed", HttpStatus.BAD_REQUEST);
            } else
                throw new CustomException("Password must have at least 1 letter.", HttpStatus.BAD_REQUEST);
        } else
            throw new CustomException("User not found.", HttpStatus.BAD_REQUEST);
    }

}
