package com.nichoshop.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.nichoshop.main.request.CreateAddressRequest;
import com.nichoshop.main.response.BasicResponse;
import com.nichoshop.main.request.ChangeAddressRequest;
import com.nichoshop.main.exception.CustomException;
import com.nichoshop.main.model.Address;
import com.nichoshop.main.model.User;
import com.nichoshop.main.service.AddressService;
import com.nichoshop.main.service.UserService;

@RestController
@RequestMapping(path = "/address")
public class AddressController {
    @Autowired
    AddressService addressService;
    @Autowired
    UserService userService;

    @PostMapping("")
    public ResponseEntity<?> createAddress(@RequestBody CreateAddressRequest createAddressRequest,
            Authentication auth) {

        // check if user exists.
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {

            // save address to db.
            Address address = new Address(user.getId(), createAddressRequest.getName(),
                    createAddressRequest.getAddress1(),
                    createAddressRequest.getAddress2(),
                    createAddressRequest.getCity(), createAddressRequest.getState(), createAddressRequest.getZip(),
                    createAddressRequest.getCountry(),
                    createAddressRequest.getPhone(), createAddressRequest.getStatus(),
                    createAddressRequest.getAddressType(), 0);

            Long addressId = addressService.createAddress(address);

            // save address to user.
            userService.addAddressToUser(user.getId(), addressId);

            // response.
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Address created.");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/changeAddress")
    public ResponseEntity<?> changeAddress(@RequestBody ChangeAddressRequest changeAddressRequest,
            Authentication auth) {

        // check if user exists.
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {

            // change address to db.
            Long addressId = user.getAddressId();
            addressService.changeAddress(
                    addressId, changeAddressRequest.getName(), changeAddressRequest.getAddress1(),
                    changeAddressRequest.getAddress2(), changeAddressRequest.getCity(),
                    changeAddressRequest.getCountry(),
                    changeAddressRequest.getState(), changeAddressRequest.getPhone(), changeAddressRequest.getZip());

            // response.
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Address updated.");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);

        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getUserAddress(Authentication auth) {
        // check if user exists.
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null)
            return new ResponseEntity<>(addressService.getAddressByUserId(user.getId()), HttpStatus.OK);
        else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateAddressStatus(@RequestParam("id") Long id, @RequestParam("status") int status,
            Authentication auth) {
        // check if user exists.
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            addressService.updateAddressStatus(id, status);

            // response.
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Address status updated.");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteAddressById(@RequestParam("id") Long id, Authentication auth) {

        // check if user exists.
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            addressService.deleteAddressById(id);

            // response.
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Address removed.");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

}
