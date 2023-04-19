package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAccumulator;

import org.joda.time.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.Item;
import com.nichoshop.main.model.ItemDeleteLog;
import com.nichoshop.main.repository.ItemDeleteLogRepository;
import com.nichoshop.main.repository.ItemRepository;
import com.nichoshop.main.request.CreateItemRequest;
import com.nichoshop.main.request.ItemSearchListingRequest;

@Service
public class ItemService {
   @Autowired
   ItemRepository itemRepository;
   @Autowired
   ItemDeleteLogRepository itemDeleteLogRepository;

   public Item getItemById(Long id) {
      return itemRepository.findById(id).get();
   }

   public List<Item> getAllItems() {
      List<Item> Items = new ArrayList<Item>();
      itemRepository.findAll().forEach(Item -> Items.add(Item));
      return Items;
   }

   public List<Item> searchItemWithStringAndCategoryId(String searchString, Long categoryId) {
      return itemRepository.findWithCategoryIdAndTitleString(searchString, categoryId);

   }

   public List<Item> searchItemWithString(String searchString) {
      return itemRepository.findWithTitleString(searchString);
   }

   public List<Item> searchItemWithCategoryId(Integer categoryId) {
      return itemRepository.findByCategoryId(categoryId);
   }

   public List<Item> getItemsByTitle(String title) {
      return itemRepository.findWithTitleString(title);
   }

   public Item createItem(CreateItemRequest createItemReq, Long userId) {
      Item item = new Item();
      item.setSellerId(userId);
      item.setCategoryId(createItemReq.getCatId());
      item.setTitle(createItemReq.getTitle());
      item.setCondId(createItemReq.getCondId());
      item.setCondDesc(createItemReq.getCondDesc());
      item.setImages(createItemReq.getImages());
      item.setImage(createItemReq.getImage());
      item.setItemDesc(createItemReq.getItemDesc());
      item.setListingFormat(createItemReq.getListingFormat());
      item.setNowPrice(createItemReq.getNowPrice());
      item.setCurrency(createItemReq.getCurrency());
      item.setQuantity(createItemReq.getQuantity());
      item.setDuration(createItemReq.getDuration());
      item.setStartPrice(createItemReq.getStartPrice());
      item.setReservePrice(createItemReq.getReservePrice());
      item.setState(createItemReq.getState());
      item.setStatus(createItemReq.getStatus());
      item.setIsMulti(createItemReq.getIsMultivariation());
      item.setNsln("This is should be calculated by server.");
      item.setDomesticService(createItemReq.getDomesticService());
      item.setDomesticServiceCost(createItemReq.getDomesticServiceCost());
      item.setAnotherService(createItemReq.getAnotherService());
      item.setAnotherServiceCost(createItemReq.getAnotherServiceCost());
      item.setLocalCollect(createItemReq.getLocalCollect());
      item.setInternationalService(createItemReq.getInternationalService());
      item.setInternationalServiceCost(createItemReq.getInternationalServiceCost());
      item.setDispatchTime(createItemReq.getDispatchTime());
      item.setItemCountry(createItemReq.getItemCountry());
      item.setItemCity(createItemReq.getItemCity());
      item.setReturnDays(createItemReq.getReturnDays());
      item.setReturns(createItemReq.getReturns());
      item.setReturnAccept(createItemReq.getReturnAccept());
      itemRepository.save(item);
      return item;
   }

   public void deleteItemsWithoutReason(List<Long> ids) {
      ids.stream().filter(id -> itemRepository.existsById(id))
            .forEach(id -> itemRepository.deleteById(id));

   }

   public List<Item> gerRecentItems(Long sellerId) {
      return itemRepository.findAccountItemRecent(sellerId);
   }

   public List<Item> getAccountItemsByState(int state, Long sellerId) {
      return itemRepository.findAccountItemByState(state, sellerId);
   }

   public List<Item> getItemsByState(int state) {
      return itemRepository.findByState(state);
   }

   public List<Item> searchMyActiveItem(Long sellerId, ItemSearchListingRequest itemSearchActiveRequest) {
      List<Item> item;
      if (itemSearchActiveRequest.getSearchType() == 0) {// title search
         if (itemSearchActiveRequest.getPriceType() == 0) {
            item = itemRepository.findAccountItemByStateAndTitleMorePriceType(0,
                  itemSearchActiveRequest.getSearchString(), itemSearchActiveRequest.getPriceType(), sellerId);
         } else if (itemSearchActiveRequest.getPriceType() < 3) {
            item = itemRepository.findAccountItemByStateAndTitleAndPriceType(0,
                  itemSearchActiveRequest.getSearchString(), itemSearchActiveRequest.getPriceType(), sellerId);
         } else if (itemSearchActiveRequest.getPriceType() == 3) { // fixed price or auction
            item = itemRepository.findAccountItemByStateAndTitleLessPriceType(0,
                  itemSearchActiveRequest.getSearchString(), itemSearchActiveRequest.getPriceType(), sellerId);
         } else {
            item = itemRepository.findAccountItemByStateAndTitleAndPriceType(0,
                  itemSearchActiveRequest.getSearchString(), itemSearchActiveRequest.getPriceType() - 1, sellerId);
         }
      } else { // nsln search
         System.out.println("nsln search");
         if (itemSearchActiveRequest.getPriceType() == 0) {
            item = itemRepository.findAccountItemByStateAndNSLNMorePriceType(0,
                  itemSearchActiveRequest.getSearchString(), itemSearchActiveRequest.getPriceType(), sellerId);
         } else if (itemSearchActiveRequest.getPriceType() < 3) {
            item = itemRepository.findAccountItemByStateAndNSLNAndPriceType(0,
                  itemSearchActiveRequest.getSearchString(), itemSearchActiveRequest.getPriceType(), sellerId);

         } else if (itemSearchActiveRequest.getPriceType() == 3) { // fixed price or auction
            item = itemRepository.findAccountItemByStateAndNSLNLessPriceType(0,
                  itemSearchActiveRequest.getSearchString(), itemSearchActiveRequest.getPriceType(), sellerId);

         } else {
            item = itemRepository.findAccountItemByStateAndNSLNAndPriceType(0,
                  itemSearchActiveRequest.getSearchString(), itemSearchActiveRequest.getPriceType() - 1, sellerId);
         }
      }
      return item;
   }

   public List<Item> searchMySoldItem(Long sellerId, ItemSearchListingRequest itemSoldRequest) {
      List<Item> item;
      DateTime now = new DateTime();
      DateTime date = now.minusDays(itemSoldRequest.getDays()).withTimeAtStartOfDay();
      if (itemSoldRequest.getSearchType() == 0) {// title search
         item = itemRepository.findAccountItemByStateAdnTitleMoreDate(1, itemSoldRequest.getSearchString(),
               date.toString(), sellerId);
      } else {
         item = itemRepository.findAccountItemByStateAndNSLNMoreDate(1, itemSoldRequest.getSearchString(),
               date.toString(), sellerId);
      }
      return item;
   }

   public List<Item> searchMyUnsoldItem(Long sellerId, ItemSearchListingRequest itemUnsoldReq) {
      List<Item> item;
      DateTime now = new DateTime();
      DateTime date = now.minusDays(itemUnsoldReq.getDays()).withTimeAtStartOfDay();
      if (itemUnsoldReq.getSearchType() == 0) {// title search
         if (itemUnsoldReq.getPriceType() == 0) {
            item = itemRepository.findAccountItemByStateAndTitleMorePriceTypeMoreDate(2,
                  itemUnsoldReq.getSearchString(), itemUnsoldReq.getPriceType(), date.toString(), sellerId);
         } else if (itemUnsoldReq.getPriceType() < 3) {
            item = itemRepository.findAccountItemByStateAndTitleAndPriceTypeMoreDate(2, itemUnsoldReq.getSearchString(),
                  itemUnsoldReq.getPriceType(), date.toString(), sellerId);
         } else if (itemUnsoldReq.getPriceType() == 3) { // fixed price or auction
            item = itemRepository.findAccountItemByStateAndTitleLessPriceTypeMoreDate(2,
                  itemUnsoldReq.getSearchString(), itemUnsoldReq.getPriceType(), date.toString(), sellerId);
         } else {
            item = itemRepository.findAccountItemByStateAndTitleAndPriceTypeMoreDate(2, itemUnsoldReq.getSearchString(),
                  itemUnsoldReq.getPriceType() - 1, date.toString(), sellerId);
         }
      } else { // nsln search
         System.out.println("nsln search");
         if (itemUnsoldReq.getPriceType() == 0) {
            item = itemRepository.findAccountItemByStateAndNSLNMorePriceTypeMoreDate(2, itemUnsoldReq.getSearchString(),
                  itemUnsoldReq.getPriceType(), date.toString(), sellerId);
         } else if (itemUnsoldReq.getPriceType() < 3) {
            item = itemRepository.findAccountItemByStateAndNSLNAndPriceTypeMoreDate(2, itemUnsoldReq.getSearchString(),
                  itemUnsoldReq.getPriceType(), date.toString(), sellerId);

         } else if (itemUnsoldReq.getPriceType() == 3) { // fixed price or auction
            item = itemRepository.findAccountItemByStateAndNSLNLessPriceTypeMoreDate(2, itemUnsoldReq.getSearchString(),
                  itemUnsoldReq.getPriceType(), date.toString(), sellerId);

         } else {
            item = itemRepository.findAccountItemByStateAndNSLNAndPriceTypeMoreDate(2, itemUnsoldReq.getSearchString(),
                  itemUnsoldReq.getPriceType() - 1, date.toString(), sellerId);
         }
      }
      return item;
   }

   public void deleteItemsByIds(List<Long> ids, String reason) {
      ids.forEach(id -> {
         Item item = itemRepository.findById(id).orElse(null);
         if (item != null) {
            ItemDeleteLog itemDeleteLog = new ItemDeleteLog();
            itemDeleteLog.setReason(reason);
            itemDeleteLog.setSellerId(item.getSellerId());
            itemDeleteLog.setItemId(item.getId());
            itemDeleteLog.setItemTitle(item.getTitle());
            itemDeleteLogRepository.save(itemDeleteLog);
            itemRepository.deleteById(item.getId());
         }
      });

   }

   public Item updateAccountItemByAmount(Long itemId, Long sellerId, int amount) {
      Item item;
      item = itemRepository.findAccountItemById(itemId, sellerId);
      if (item != null) {
         item.setQuantity(amount);
         itemRepository.save(item);
      }
      return item;
   }

   public void saveOrUpdate(Item Item) {
      itemRepository.save(Item);
   }

   public void updateItemState(Long itemId, int state) {
      Item itemFromDb = itemRepository.findById(itemId).get();
      itemFromDb.setState(state);
      itemRepository.save(itemFromDb);
   }

   public List<Item> findAll() {
      return itemRepository.findAll();
   }

}
