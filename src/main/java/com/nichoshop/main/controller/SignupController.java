package com.nichoshop.main.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.nichoshop.main.util.SecureUtils;

import lombok.RequiredArgsConstructor;

import com.nichoshop.main.util.CaptchaClient;
import com.nichoshop.main.util.Constants;
import com.nichoshop.main.util.Emailer;
import com.nichoshop.main.util.Environment;
import com.nichoshop.main.request.SignupRequest;
import com.nichoshop.main.response.BasicResponse;
import com.nichoshop.main.response.SignUpResponse;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.nichoshop.main.exception.CustomException;
import com.nichoshop.main.model.EmailConfirm;
import com.nichoshop.main.model.User;
import com.nichoshop.main.service.EmailConfirmService;
import com.nichoshop.main.service.IPLocationService;
import com.nichoshop.main.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/signup")
public class SignupController {
    @Autowired
    UserService userService;
    @Autowired
    EmailConfirmService emailConfirmService;
    private final PasswordEncoder passwordEncoder;
    private IPLocationService locationService;

    @PostConstruct
    public void initializeDuoClient() throws IOException {
        locationService = new IPLocationService();
    }

    @PostMapping("")
    public ResponseEntity<?> signUpUser(@RequestBody SignupRequest signupRequest, HttpServletRequest request)
            throws NoSuchAlgorithmException, IOException, GeoIp2Exception {

        String ip = SecureUtils.getIpFromRequest(request);

        String countryCode = locationService.getLocation(ip).getCountry();
        Boolean captcha = Environment.skipCaptcha || CaptchaClient.checkCaptchaV2(signupRequest.getGrecaptcha());
        SignUpResponse signupResponse = new SignUpResponse();
        if (captcha) {
            if (signupRequest.getEmail() == null || signupRequest.getEmail().equals("") ||
                    signupRequest.getName() == null || signupRequest.getName().equals("") ||
                    signupRequest.getPassword() == null || signupRequest.getPassword().equals("")) {
                throw new CustomException("Invalid username/password supplied", HttpStatus.BAD_REQUEST);
            } else {
                if (!userService.checkEmailAlreadyExists(signupRequest.getEmail())) {
                    // generate randome username max 10 times
                    String username = SecureUtils.generateUsername(signupRequest.getName(), countryCode);

                    int i = 0;
                    while (userService.checkUsernameAlreadyExists(username) && i < Constants.randomNameCheckIteration) {
                        username = SecureUtils.generateUsername(signupRequest.getName(), countryCode);
                        i += 1;
                    }
                    // check if email or username exists.
                    if (!userService.checkUsernameAlreadyExists(username)) {

                        String[] names = signupRequest.getName().split(" ");
                        String fname = names[0];
                        String lname = names[1];

                        if (fname == null || lname == null) {
                            throw new CustomException("Invalid User", HttpStatus.BAD_REQUEST);
                        }
                        User user = new User(username, passwordEncoder.encode(signupRequest.getPassword()),
                                signupRequest.getEmail(), fname,
                                lname, signupRequest.getAccountType());
                        userService.saveOrUpdate(user);

                        // check if saved.
                        Long userId = userService.getUserByUsernameOrEmail(user.getEmail()).getId();

                        // generate randome suc.
                        String code = SecureUtils.generateMD5Token(user.getUsername());

                        // save email confirmation code.
                        EmailConfirm emailConfirm = new EmailConfirm();
                        emailConfirm.setCode(code);
                        emailConfirm.setUserId(userId);
                        userService.saveEmailConfirm(emailConfirm);

                        // send email to user.
                        try {
                            Emailer.sendEmailConfirmation(user, code);
                        } catch (EmailException e) {
                            e.printStackTrace();
                        }
                        signupResponse.setResult("success");
                        signupResponse.setEmail(user.getEmail());
                        signupResponse.setUsername(user.getUsername());

                        return new ResponseEntity<>(signupResponse, HttpStatus.OK);
                    } else
                        throw new CustomException("Email already exists.", HttpStatus.BAD_REQUEST);
                } else
                    throw new CustomException("Email already exists.", HttpStatus.BAD_REQUEST);
            }
        } else
            throw new CustomException("Captcha failed.", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/resend-email")
    public ResponseEntity<?> resendEmailInSignUp(@RequestParam("user") String email) {

        // get user by email.
        User user = userService.getUserByUsernameOrEmail(email);

        // check if not null
        if (user != null) {

            // get email confirmation code.
            String code = userService.getEmailConfirmCode(user.getId());
            try {
                Emailer.sendEmailConfirmation(user, code);
                BasicResponse basicResponse = new BasicResponse();
                basicResponse.setMessage("Email sent");
                return new ResponseEntity<>(basicResponse, HttpStatus.OK);
            } catch (EmailException e) {
                throw new CustomException("Email not sent.", HttpStatus.BAD_REQUEST);
            }
        } else
            throw new CustomException("Email not sent.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmCodeFromEmail(@RequestParam("code") String code) {
        try {
            Boolean result = userService.confirmEmail(code);

            if (result) {
                BasicResponse basicResponse = new BasicResponse();
                basicResponse.setMessage("User verified.");
                return new ResponseEntity<>(basicResponse, HttpStatus.OK);
            } else
                throw new CustomException("User not verified", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            throw new CustomException("User not verified", HttpStatus.BAD_REQUEST);
        }
    }

}
