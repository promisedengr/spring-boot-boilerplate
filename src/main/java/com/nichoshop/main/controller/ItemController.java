package com.nichoshop.main.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.nichoshop.main.service.BidService;
import com.nichoshop.main.service.CartService;
import com.nichoshop.main.service.FeedBackService;
import com.nichoshop.main.service.FileStorageService;
import com.nichoshop.main.service.ItemService;
import com.nichoshop.main.service.MessageService;
import com.nichoshop.main.service.ReportService;
import com.nichoshop.main.service.UserService;
import com.nichoshop.main.service.WatchListService;
import com.nichoshop.main.util.Constants;

import lombok.RequiredArgsConstructor;

import com.nichoshop.main.exception.CustomException;
import com.nichoshop.main.model.Bid;
import com.nichoshop.main.model.Cart;
import com.nichoshop.main.model.FeedBack;
import com.nichoshop.main.model.Item;
import com.nichoshop.main.request.AddCartRequest;
import com.nichoshop.main.request.CreateItemRequest;
import com.nichoshop.main.request.CreatemessageRequest;
import com.nichoshop.main.request.DeleteIdsRequest;
import com.nichoshop.main.request.FeedBackRequest;
import com.nichoshop.main.request.ItemSearchListingRequest;
import com.nichoshop.main.request.ReportRequest;
import com.nichoshop.main.request.SubmitBidRequest;
import com.nichoshop.main.response.BasicResponse;
import com.nichoshop.main.response.ItemStateCountResponse;

import com.nichoshop.main.model.Message;
import com.nichoshop.main.model.Report;
import com.nichoshop.main.model.User;
import com.nichoshop.main.model.WatchList;

@RestController
@RequestMapping(path = "/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    CartService cartService;

    @Autowired
    BidService bidService;

    @Autowired
    ReportService reportService;

    @Autowired
    FeedBackService feedBackService;

    @Autowired
    WatchListService watchListService;
    private FileStorageService fileStorageService;

    public ItemController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createItem(@RequestBody CreateItemRequest createItemReq, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {

            Item item = itemService.createItem(createItemReq, user.getId());
            if (item != null) {
                BasicResponse basicResponse = new BasicResponse();
                basicResponse.setMessage("Item created");
                return new ResponseEntity<>(basicResponse, HttpStatus.OK);
            } else
                throw new CustomException("Item not created.", HttpStatus.BAD_REQUEST);
        } else
            throw new CustomException("User not found.", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("")
    public ResponseEntity<?> getArchivedProduct(@RequestParam("filter") int state, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            return new ResponseEntity<>(itemService.getItemsByState(state), HttpStatus.OK);
        } else
            throw new CustomException("User not found.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentItem(Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            return new ResponseEntity<>(itemService.gerRecentItems(user.getId()), HttpStatus.OK);
        } else
            throw new CustomException("User not found.", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/search/count")
    public ResponseEntity<?> getItemCountByState(Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {

            ItemStateCountResponse itemStateCountResponse = new ItemStateCountResponse();
            itemStateCountResponse.setActiveCnt(itemService.getAccountItemsByState(0, user.getId()).size());
            itemStateCountResponse.setOutOfStockCnt(itemService.getAccountItemsByState(1, user.getId()).size());
            itemStateCountResponse.setUnSoldCnt(itemService.getAccountItemsByState(2, user.getId()).size());
            return new ResponseEntity<>(itemStateCountResponse, HttpStatus.OK);
        } else
            throw new CustomException("User not found.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/search/active")
    public ResponseEntity<?> searchMyActiveItem(@RequestBody ItemSearchListingRequest itemSearchActiveReq,
            Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            return new ResponseEntity<>(itemService.searchMyActiveItem(user.getId(), itemSearchActiveReq),
                    HttpStatus.OK);
        } else
            throw new CustomException("User not found.", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/search/sold")
    public ResponseEntity<?> searchMySoldItem(@RequestBody ItemSearchListingRequest itemSoldReq, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            return new ResponseEntity<>(itemService.searchMySoldItem(user.getId(), itemSoldReq), HttpStatus.OK);
        } else
            throw new CustomException("User not found.", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/search/unsold")
    public ResponseEntity<?> searchMyUnsoldItem(@RequestBody ItemSearchListingRequest itemUnsoldReq,
            Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            return new ResponseEntity<>(itemService.searchMyUnsoldItem(user.getId(), itemUnsoldReq), HttpStatus.OK);

        } else
            throw new CustomException("User not found.", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteItemsById(@RequestBody DeleteIdsRequest deleteIdsRequest, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            if (deleteIdsRequest.getIds().size() > 0) {
                itemService.deleteItemsByIds(deleteIdsRequest.getIds(), deleteIdsRequest.getReason());

                // response.
                BasicResponse basicResponse = new BasicResponse();
                basicResponse.setMessage("Successfully deleted.");
                return new ResponseEntity<>(basicResponse, HttpStatus.OK);

            } else
                throw new CustomException("Cannot delete.", HttpStatus.BAD_REQUEST);

        } else
            throw new CustomException("User not found.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("account/update/")
    public ResponseEntity<?> updateAccountItemByAmount(@RequestParam("id") Long itemId,
            @RequestParam("amount") int amount, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            Item item = itemService.updateAccountItemByAmount(itemId, user.getId(), amount);
            if (item != null) {

                // response.
                BasicResponse basicResponse = new BasicResponse();
                basicResponse.setMessage("Item updated");
                return new ResponseEntity<BasicResponse>(basicResponse, HttpStatus.BAD_REQUEST);

            } else
                throw new CustomException("Item not found.", HttpStatus.BAD_REQUEST);

        } else
            throw new CustomException("User not found.", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteItemsWithoutReason(@RequestBody DeleteIdsRequest deleteIdsRequest,
            Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            if (deleteIdsRequest.getIds().size() > 0) {
                itemService.deleteItemsWithoutReason(deleteIdsRequest.getIds());

                // response.
                BasicResponse basicResponse = new BasicResponse();
                basicResponse.setMessage("Successfully deleted.");
                return new ResponseEntity<>(basicResponse, HttpStatus.OK);

            } else
                throw new CustomException("Cannot delete.", HttpStatus.BAD_REQUEST);

        } else
            throw new CustomException("User not found.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/search")
    public List<Item> searchItem(@RequestParam Map<String, String> searchParams) {

        if (searchParams.get("keyword") != null && searchParams.get("catId") != null) {
            String searchString = searchParams.get("keyword");
            Long categoryId = Long.parseLong(searchParams.get("catId"));
            return itemService.searchItemWithStringAndCategoryId(searchString, categoryId);
        } else if (searchParams.get("catId") == null) {
            String searchString = searchParams.get("keyword");
            return itemService.searchItemWithString(searchString);
        } else if (searchParams.get("keyword") == null) {
            Integer categoryId = Integer.parseInt(searchParams.get("catId"));
            return itemService.searchItemWithCategoryId(categoryId);
        } else {
            return itemService.getAllItems();
        }
    }

    @PostMapping("/setting/sold")
    public ResponseEntity<?> setItemSold(@RequestParam("id") Long itemId, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            itemService.updateItemState(itemId, 1);

            // response.
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Successfully updated.");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);

        } else
            throw new CustomException("User not found.", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/contact-seller")
    public ResponseEntity<?> contactSeller(@RequestBody CreatemessageRequest createMessageRequest,
            Authentication auth) {

        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            MultipartFile[] files = createMessageRequest.getFiles();
            List<String> attached = new ArrayList<String>();

            try {
                Arrays.asList(files).stream().forEach(file -> {
                    String fileName = fileStorageService.storeFile(file);
                    attached.add(fileName);
                });

                Message message = new Message();
                message.setAttached(attached);
                message.setFromName(user.getUsername());
                message.setFromId(user.getId());
                message.setToId(createMessageRequest.getToId());
                message.setSubject(createMessageRequest.getTopic());
                message.setContent(createMessageRequest.getContent());
                message.setItemId(createMessageRequest.getItemId());
                message.setItemTitle(createMessageRequest.getItemTitle());
                message.setMessageType(Constants.MessageType.contactSeller);

                messageService.saveOrUpdate(message);

                BasicResponse basicResponse = new BasicResponse();
                basicResponse.setMessage("Contact seller message sent.");
                return new ResponseEntity<>(basicResponse, HttpStatus.OK);

            } catch (Exception e) {
                throw new CustomException("Item not found", HttpStatus.BAD_REQUEST);
            }
        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addCart(@RequestBody AddCartRequest addCartRequest, Authentication auth) {

        // get item from id
        Item item = itemService.getItemById(addCartRequest.getItemId());

        // check if not null.
        if (item != null) {

            // get user from auth.
            User user = userService.getUserByUsernameOrEmail(auth.getName());

            // check if not null
            if (user != null) {

                // create cart
                Cart cart = new Cart();
                cart.setItemId(addCartRequest.getItemId());
                cart.setUserId(user.getId());
                cart.setQty(addCartRequest.getQuantity());

                cartService.saveOrUpdate(cart);

                // response.
                BasicResponse basicResponse = new BasicResponse();
                basicResponse.setMessage("Added to Cart.");
                return new ResponseEntity<>(basicResponse, HttpStatus.OK);
            } else
                throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
        } else
            throw new CustomException("Item not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/report")
    public ResponseEntity<?> report(@RequestBody ReportRequest reportRequest, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            Report report = new Report();
            report.setItemId(reportRequest.getItemId());
            report.setReportType(reportRequest.getReportType());
            report.setDetailType(reportRequest.getDetailType());
            report.setSpecificType(reportRequest.getSpecificType());
            report.setContent(reportRequest.getContent());
            report.setUserId(user.getId());

            reportService.saveOrUpdate(report);

            Message message = new Message();
            message.setFromId(user.getId());
            message.setFromName(user.getUsername());
            message.setItemId(reportRequest.getItemId());
            message.setMessageType(Constants.MessageType.reportSeller);
            message.setContent("Your item was reported"); // TODO: should be replaced by Constant message.

            messageService.saveOrUpdate(message);
            // response.
            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Report sent.");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/feedback")
    public ResponseEntity<?> leaveFeedBack(@RequestBody FeedBackRequest feedBackRequest, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            FeedBack feedBack = new FeedBack();
            feedBack.setContent(feedBackRequest.getContent());
            feedBack.setItemId(feedBackRequest.getItemId());
            feedBack.setUserId(user.getId());
            feedBack.setRate(feedBackRequest.getRate());

            feedBackService.saveOrUpdate(feedBack);

            Message message = new Message();
            message.setFromId(user.getId());
            message.setFromName(user.getUsername());
            message.setItemId(feedBackRequest.getItemId());
            message.setMessageType(Constants.MessageType.reviseFeedBack);
            message.setContent("Buyer's feedback was revised"); // TODO: should be replaced by Constant message.

            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Feedback requested.");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        }
        throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/revise-feedback")
    public ResponseEntity<?> reviseFeedback(@RequestBody FeedBackRequest feedBackRequest, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {

            FeedBack feedBack = feedBackService.getFeedBackById(feedBackRequest.getId());
            feedBack.setContent(feedBackRequest.getContent());
            feedBack.setItemId(feedBackRequest.getItemId());
            feedBack.setUserId(user.getId());
            feedBack.setRate(feedBackRequest.getRate());

            feedBackService.saveOrUpdate(feedBack);

            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Feedback requested.");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);
        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/watchlist")
    public ResponseEntity<?> addWatchList(@RequestParam("id") Long itemId, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            WatchList watchList = new WatchList();
            watchList.setItemId(itemId);
            watchList.setUserId(user.getId());

            watchListService.saveOrUpdate(watchList);

            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Added to watchList.");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);

        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/watchlist")
    public ResponseEntity<?> removeWatchList(@RequestParam("id") Long id, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            watchListService.deleteWatchListById(id);

            BasicResponse basicResponse = new BasicResponse();
            basicResponse.setMessage("Removed from watchList.");
            return new ResponseEntity<>(basicResponse, HttpStatus.OK);

        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/contact-user")
    public ResponseEntity<?> contacBuyer(@RequestBody CreatemessageRequest createMessageRequest, Authentication auth) {
        User user = userService.getUserByUsernameOrEmail(auth.getName());
        if (user != null) {
            MultipartFile[] files = createMessageRequest.getFiles();
            List<String> attached = new ArrayList<String>();

            try {
                Arrays.asList(files).stream().forEach(file -> {
                    String fileName = fileStorageService.storeFile(file);
                    attached.add(fileName);
                });

                Message message = new Message();
                message.setAttached(attached);
                message.setFromName(user.getUsername());
                message.setFromId(user.getId());
                message.setToId(createMessageRequest.getToId());
                message.setSubject(Constants.MessageTopic.contactUser);
                message.setContent(createMessageRequest.getContent());
                message.setMessageType(Constants.MessageType.contactUser);

                messageService.saveOrUpdate(message);

                BasicResponse basicResponse = new BasicResponse();
                basicResponse.setMessage("Contact buyer message sent.");
                return new ResponseEntity<>(basicResponse, HttpStatus.OK);

            } catch (Exception e) {
                throw new CustomException("Item not found", HttpStatus.BAD_REQUEST);
            }
        } else
            throw new CustomException("User not found", HttpStatus.BAD_REQUEST);
    }
}
