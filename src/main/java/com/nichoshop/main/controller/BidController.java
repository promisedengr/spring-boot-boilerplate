package com.nichoshop.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.nichoshop.main.service.BidService;
import com.nichoshop.main.service.ItemService;
import com.nichoshop.main.service.UserService;
import com.nichoshop.main.exception.CustomException;
import com.nichoshop.main.model.Bid;
import com.nichoshop.main.model.Item;
import com.nichoshop.main.model.User;
import com.nichoshop.main.request.SubmitBidRequest;
import com.nichoshop.main.response.BasicResponse;

@RestController
@RequestMapping(path = "/bid")
public class BidController {
    @Autowired
    BidService bidService;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getBidList(@RequestParam("item_id") Long itemId, Authentication auth) {

        // get user from auth.
        User user = userService.getUserByUsernameOrEmail(auth.getName());

        // check if not null.
        if (user != null) {

            // response.
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Get All bids for current item.");
            return new ResponseEntity<>(bidService.getAllBidsByItemId(itemId), HttpStatus.BAD_REQUEST);
        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitBid(SubmitBidRequest submitBidRequest, Authentication auth) {

        // TODO: unpaid records check.

        // get item from id
        Item item = itemService.getItemById(submitBidRequest.getItemId());

        // check if not null.
        if (item != null) {

            // get user from auth.
            User user = userService.getUserByUsernameOrEmail(auth.getName());

            // check if not null
            if (user != null) {

                // create cart
                Bid bid = new Bid();
                bid.setItemId(submitBidRequest.getItemId());
                bid.setUserId(user.getId());
                bid.setAmount(submitBidRequest.getAmount());
                bid.setCurrencyId(submitBidRequest.getCurrencyId());

                bidService.saveOrUpdate(bid);

                // response.
                BasicResponse basicResponse = new BasicResponse();
                basicResponse.setMessage("Added to Cart.");
                return new ResponseEntity<>(basicResponse, HttpStatus.OK);
            } else
                throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
        } else
            throw new CustomException("Item not found", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("")
    public ResponseEntity<?> cancelBid(@RequestParam("id") Long bidId, Authentication auth) {
        // get user from auth.
        User user = userService.getUserByUsernameOrEmail(auth.getName());

        // check if not null
        if (user != null) {

            if (bidService.isCancellable(bidId)) {

                Bid bid = bidService.setCancelStatus(bidId);

                if (bid != null) {
                    BasicResponse basicResponse = new BasicResponse();
                    basicResponse.setMessage("Bid cancelled.");
                    return new ResponseEntity<>(basicResponse, HttpStatus.OK);
                } else
                    throw new CustomException("Bid not found", HttpStatus.BAD_REQUEST);
            } else
                throw new CustomException("Bid not cancellable.", HttpStatus.BAD_REQUEST);
        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

}
