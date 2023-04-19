package com.nichoshop.main.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;

import com.nichoshop.main.response.BasicResponse;
import com.nichoshop.main.service.CustomerSupportService;
import com.nichoshop.main.service.UserService;
import com.nichoshop.main.request.CSCreateRequest;
import com.nichoshop.main.exception.CustomException;
import com.nichoshop.main.model.CustomerSupport;
import com.nichoshop.main.model.User;
import com.nichoshop.main.request.AdminNoteCreateRequest;

@RestController
@RequestMapping(path = "/admin/customersupport")
public class AdminCSController {
    @Autowired
    CustomerSupportService cSupportService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createCustomerSupport(@RequestBody CSCreateRequest csCreateRequest, Authentication auth) {

        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {

            CustomerSupport customerSupport = new CustomerSupport();

            customerSupport.setAdminId(user.getId());
            customerSupport.setFname(csCreateRequest.getFname());
            customerSupport.setLname(csCreateRequest.getEmail());
            customerSupport.setEmail(csCreateRequest.getEmail());
            customerSupport.setPassword(csCreateRequest.getPassword());
            customerSupport.setPhone(csCreateRequest.getPhone());
            customerSupport.setDepart(csCreateRequest.getDepart());
            customerSupport.setSubDepart(csCreateRequest.getSubDepart());
            customerSupport.setTimezone(csCreateRequest.getTimezone());
            customerSupport.setContact(csCreateRequest.getContact());
            customerSupport.setMonToFri(csCreateRequest.getMonToFri());
            customerSupport.setSaturday(csCreateRequest.getSaturday());
            customerSupport.setSunday(csCreateRequest.getSunday());
            customerSupport.setCurrencyId(csCreateRequest.getCurrencyId());

            cSupportService.saveOrUpdate(customerSupport);

            // response.
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("successfully crated");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } else
            throw new CustomException("Admin not found", HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> getAllCustomerSupports(Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null)
            return new ResponseEntity<>(cSupportService.getAllCustomerSupports(), HttpStatus.OK);
        else
            throw new CustomException("Admin not found.", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/note")
    public ResponseEntity<?> createAdminNote(@RequestBody AdminNoteCreateRequest adminNoteCreateReq,
            Authentication auth) {

        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {

            cSupportService.createAdminNote(user.getId(), adminNoteCreateReq);

            // response.
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Admin note created.");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } else
            throw new CustomException("Admin not found.", HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/note")
    public ResponseEntity<?> getAdminNote(@RequestParam("cs_id") Long csId, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null)
            return new ResponseEntity<>(cSupportService.getAdminNoteBycsId(csId), HttpStatus.OK);
        else
            throw new CustomException("Admin not found.", HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/note")
    public ResponseEntity<?> updateAdminNote(@RequestParam("id") Long noteId,
            @RequestBody AdminNoteCreateRequest adminNoteCreateReq, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            cSupportService.updateAdminNote(noteId, adminNoteCreateReq);

            // response.
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Admin Note updated.");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } else
            throw new CustomException("Admin not found.", HttpStatus.BAD_REQUEST);
    }

}
