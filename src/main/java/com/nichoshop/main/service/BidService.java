package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.Bid;
import com.nichoshop.main.repository.BidRepository;
import com.nichoshop.main.request.CreateBidRequest;
import com.nichoshop.main.util.Constants;

@Service
public class BidService {
   @Autowired
   BidRepository bidRepository;

   public Bid getBidById(Long id) {
      return bidRepository.findById(id).get();
   }

   public List<Bid> getAllBidsByItemId(Long itemId) {
      return bidRepository.findAllByItemId(itemId);
   }

   public List<Bid> getAllBids() {
      List<Bid> Bids = new ArrayList<Bid>();
      bidRepository.findAll().forEach(Bid -> Bids.add(Bid));
      return Bids;
   }

   public Bid setCancelStatus(Long bidId) {
      Bid bid = bidRepository.findById(bidId).orElse(null);
      if (bid != null) {
         bid.setStatus(false);
         bidRepository.save(bid);
      }
      return bid;
   }

   public Boolean isCancellable(Long id) {

      // check if not null.
      Bid bid = bidRepository.findById(id).orElse(null);
      if (bid != null) {

         // check the duration
         Long duration = System.currentTimeMillis() - bid.getCreatedAt().getTime();
         if (duration < Constants.cancelBidBlock) {
            return true;
         }
      }
      return false;
   }

   public void saveOrUpdate(Bid Bid) {
      bidRepository.save(Bid);
   }

   public void deleteBidById(Long id) {
      bidRepository.deleteById(id);
   }
}
