package com.nichoshop.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nichoshop.main.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM order_items WHERE seller_id =?1 ORDER BY sold_date ASC", nativeQuery = true)
    public List<Order> findSellerOrderSoldDateASC(Long sellerId);

    @Query(value = "SELECT * FROM order_items WHERE seller_id =?1 ORDER BY sold_date DESC", nativeQuery = true)
    public List<Order> findSellerOrderSoldDateDESC(Long sellerId);

    @Query(value = "SELECT * FROM order_items WHERE seller_id =?1 ORDER BY ship_by_date ASC", nativeQuery = true)
    public List<Order> findSellerOrderShipByDateASC(Long sellerId);

    @Query(value = "SELECT * FROM order_items WHERE seller_id =?1 ORDER BY ship_by_date DESC", nativeQuery = true)
    public List<Order> findSellerOrderShipByDateDESC(Long sellerId);

    @Query(value = "SELECT * FROM order_items WHERE seller_id =?1 AND order_id = ?2", nativeQuery = true)
    public Order findBySellerIdANDOrderId(Long sellerId, Long orderId);

}