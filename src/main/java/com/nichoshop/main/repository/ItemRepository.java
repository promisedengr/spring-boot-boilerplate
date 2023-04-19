package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.nichoshop.main.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // Sort sort = new Sort(Direction.ASC, "firstName");

    @Query(value = "SELECT * FROM items WHERE title LIKE CONCAT('%',?1,'%') AND cat_id = ?2", nativeQuery = true)
    List<Item> findWithCategoryIdAndTitleString(String searchString, Long categoryId);

    @Query(value = "SELECT * FROM items WHERE title LIKE CONCAT('%',?1,'%')", nativeQuery = true)
    List<Item> findWithTitleString(String searchString);

    public List<Item> findByCategoryId(Integer categoryId);

    @Query(value = "SELECT * FROM items WHERE state =?1 AND quantity != ?2", nativeQuery = true)
    public List<Item> findItemByStateNOTQuantity(Integer state, Integer quantity);

    @Query(value = "SELECT * FROM items WHERE state =?1 AND quantity = ?2", nativeQuery = true)
    public List<Item> findItemByStateANDQuantity(Integer state, Integer quantity);

    public List<Item> findByState(int state);

    // Search Account Item
    @Query(value = "SELECT * FROM items WHERE seller_id =?2 AND state = ?1", nativeQuery = true)
    public List<Item> findAccountItemByState(Integer state, Long sellerId);

    @Query(value = "SELECT * FROM items WHERE seller_id =?1 ORDER BY created_at DESC LIMIT 10", nativeQuery = true)
    public List<Item> findAccountItemRecent(Long sellerId);

    @Query(value = "SELECT * FROM items WHERE title LIKE CONCAT('%',?2,'%') AND state = ?1 AND price_type = ?3 AND seller_id = ?4", nativeQuery = true)
    public List<Item> findAccountItemByStateAndTitleAndPriceType(Integer state, String title, Integer priceType,
            Long sellerId);

    @Query(value = "SELECT * FROM items WHERE title LIKE CONCAT('%',?2,'%') AND state = ?1 AND price_type > ?3 AND seller_id = ?4", nativeQuery = true)
    public List<Item> findAccountItemByStateAndTitleMorePriceType(Integer state, String title, Integer priceType,
            Long sellerId);

    @Query(value = "SELECT * FROM items WHERE title LIKE CONCAT('%',?2,'%') AND state = ?1 AND price_type < ?3 AND seller_id = ?4", nativeQuery = true)
    public List<Item> findAccountItemByStateAndTitleLessPriceType(Integer state, String title, Integer priceType,
            Long sellerId);

    @Query(value = "SELECT * FROM items WHERE nsln LIKE CONCAT('%',?2,'%') AND state = ?1 AND price_type = ?3 AND seller_id = ?4", nativeQuery = true)
    public List<Item> findAccountItemByStateAndNSLNAndPriceType(Integer state, String nsln, Integer priceType,
            Long sellerId);

    @Query(value = "SELECT * FROM items WHERE nsln LIKE CONCAT('%',?2,'%') AND state = ?1 AND price_type > ?3 AND seller_id = ?4", nativeQuery = true)
    public List<Item> findAccountItemByStateAndNSLNMorePriceType(Integer state, String nsln, Integer priceType,
            Long sellerId);

    @Query(value = "SELECT * FROM items WHERE nsln LIKE CONCAT('%',?2,'%') AND state = ?1 AND price_type < ?3 AND seller_id = ?4", nativeQuery = true)
    public List<Item> findAccountItemByStateAndNSLNLessPriceType(Integer state, String nsln, Integer priceType,
            Long sellerId);

    @Query(value = "SELECT * FROM items WHERE title LIKE CONCAT('%',?2,'%') AND state = ?1 AND created_at > ?3 AND seller_id = ?4", nativeQuery = true)
    public List<Item> findAccountItemByStateAdnTitleMoreDate(Integer state, String title, String date,
            Long sellerId);

    @Query(value = "SELECT * FROM items WHERE nsln LIKE CONCAT('%',?2,'%') AND state = ?1 AND created_at > ?3 AND seller_id = ?4", nativeQuery = true)
    public List<Item> findAccountItemByStateAndNSLNMoreDate(Integer state, String title, String date, Long sellerId);

    @Query(value = "SELECT * FROM items WHERE title LIKE CONCAT('%',?2,'%') AND state = ?1 AND price_type = ?3 AND created_at > ?4 AND seller_id = ?5", nativeQuery = true)
    public List<Item> findAccountItemByStateAndTitleAndPriceTypeMoreDate(Integer state, String title, Integer priceType,
            String date, Long sellerId);

    @Query(value = "SELECT * FROM items WHERE title LIKE CONCAT('%',?2,'%') AND state = ?1 AND price_type > ?3 AND created_at > ?4 AND seller_id = ?5", nativeQuery = true)
    public List<Item> findAccountItemByStateAndTitleMorePriceTypeMoreDate(Integer state, String title,
            Integer priceType, String date, Long sellerId);

    @Query(value = "SELECT * FROM items WHERE title LIKE CONCAT('%',?2,'%') AND state = ?1 AND price_type < ?3 AND created_at > ?4 AND seller_id = ?5", nativeQuery = true)
    public List<Item> findAccountItemByStateAndTitleLessPriceTypeMoreDate(Integer state, String title,
            Integer priceType, String date, Long sellerId);

    @Query(value = "SELECT * FROM items WHERE nsln LIKE CONCAT('%',?2,'%') AND state = ?1 AND price_type = ?3 AND created_at > ?4 AND seller_id = ?5", nativeQuery = true)
    public List<Item> findAccountItemByStateAndNSLNAndPriceTypeMoreDate(Integer state, String nsln, Integer priceType,
            String date, Long sellerId);

    @Query(value = "SELECT * FROM items WHERE nsln LIKE CONCAT('%',?2,'%') AND state = ?1 AND price_type > ?3 AND created_at > ?4 AND seller_id = ?5", nativeQuery = true)
    public List<Item> findAccountItemByStateAndNSLNMorePriceTypeMoreDate(Integer state, String nsln, Integer priceType,
            String date, Long sellerId);

    @Query(value = "SELECT * FROM items WHERE nsln LIKE CONCAT('%',?2,'%') AND state = ?1 AND price_type < ?3 AND created_at > ?4 AND seller_id = ?5", nativeQuery = true)
    public List<Item> findAccountItemByStateAndNSLNLessPriceTypeMoreDate(Integer state, String nsln, Integer priceType,
            String date, Long sellerId);

    @Query(value = "SELECT * FROM items WHERE id =?1 AND seller_id = ?2", nativeQuery = true)
    public Item findAccountItemById(Long itemId, Long sellerId);

}
