package com.nichoshop.main.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.mindrot.jbcrypt.BCrypt;
import org.apache.commons.mail.EmailException;

import java.util.List;

import com.nichoshop.main.exception.CustomException;
import com.nichoshop.main.model.SUC;
import com.nichoshop.main.model.User;
import com.nichoshop.main.request.NewPasswordRequest;
import com.nichoshop.main.response.BasicResponse;
import com.nichoshop.main.service.UserService;
import com.nichoshop.main.util.Constants;
import com.nichoshop.main.util.Emailer;
import com.nichoshop.main.util.SecureUtils;
import com.nichoshop.main.util.Twilio;
import com.nichoshop.main.service.SUCService;

@RestController
@RequestMapping(path = "/admin/administrator")
public class AdminAdministrator {
    @Autowired
    UserService userService;
    @Autowired
    SUCService sucService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/upgrade/password") // set security for this route
    public ResponseEntity<?> changePassword(@RequestBody NewPasswordRequest newPasswordRequest) {
        BasicResponse basicResponse = new BasicResponse();

        User user = userService.getUserByUsernameOrEmail(newPasswordRequest.getUsername());
        if (user != null) {
            if (!BCrypt.checkpw(newPasswordRequest.getCurPass(), user.getPassword())) {
                basicResponse.setMessage("A wrong password: " + newPasswordRequest.getUsername());
                return new ResponseEntity<BasicResponse>(basicResponse, HttpStatus.BAD_REQUEST);
            } else {
                user.setPassword(newPasswordRequest.getNewPass());
                userService.saveOrUpdate(user);

                return new ResponseEntity<User>(user, HttpStatus.OK);
            }

        } else {
            basicResponse.setMessage("A user doesn't find with username: " + newPasswordRequest.getUsername());
            return new ResponseEntity<BasicResponse>(basicResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/upgrade/email") // set security for this route
    public ResponseEntity<?> upgradeEmail(@RequestParam("value") String email, Authentication auth) {

        // get user from auth.
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {

            // set new email.
            if (userService.checkEmailAlreadyExists(email)) {

                user.setEmail(email);
                userService.saveOrUpdate(user);
                List<String> usedSUCList = sucService.getSUCsByUserId(user.getId());
                String code = SecureUtils.generateSUC(usedSUCList);

                // save suc to database
                SUC suc = new SUC();
                suc.setUserId(user.getId());
                suc.setCode(code);
                suc.setSucType(Constants.sucType.adminChangeEmail);
                sucService.saveOrUpdate(suc);

                // send message by Twilio.
                try {
                    Emailer.sendSUCConfirmation(user, code);

                    // response.
                    BasicResponse basicResponse = new BasicResponse();
                    basicResponse.setMessage("A SUC code is sent.");
                    return new ResponseEntity<BasicResponse>(basicResponse, HttpStatus.OK);
                } catch (EmailException e) {
                    throw new CustomException("Email not sent", HttpStatus.BAD_REQUEST);
                }
            } else
                throw new CustomException("Email is already exist", HttpStatus.BAD_REQUEST);
        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/upgrade/phone") // set security for this route
    public ResponseEntity<?> upgradePhoneNum(@RequestParam("value") String phoneNum, Authentication auth) {

        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {

            // check if the phone number is valid
            if (SecureUtils.isValidPhoneNumber(phoneNum)) {

                user.setPhone(phoneNum);
                userService.saveOrUpdate(user);

                // get suc list used by this user.
                List<String> usedSUCList = sucService.getSUCsByUserId(user.getId());

                // get random suc.
                String code = SecureUtils.generateSUC(usedSUCList);

                // save to database.
                SUC suc = new SUC();
                suc.setUserId(user.getId());
                suc.setCode(code);
                suc.setSucType(Constants.sucType.adminChangePhone);

                sucService.saveOrUpdate(suc);

                // send twilio
                Twilio twilio = new Twilio();
                twilio.sendSMSToNumber(user.getPhone(), code);

                // response.
                BasicResponse basicResponse = new BasicResponse();
                basicResponse.setMessage("A SUC code is sent.");
                return new ResponseEntity<BasicResponse>(basicResponse, HttpStatus.OK);
            } else
                throw new CustomException("Invalid phone number", HttpStatus.BAD_REQUEST);
        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

}
