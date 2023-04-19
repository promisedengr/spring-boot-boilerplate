package com.nichoshop.main.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

@Data
@Entity
@Table(name = "order_items")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column private Long id;
    @Column(name = "buyer_id") private int buyerId;
    @Column(name = "buyer_name") private String buyerName;
    @Column(name = "seller_name") private String sellerName;
    @Column(name = "seller_id") private int sellerId;
    @Column private int payment;
    @Column private Long delivery;
    @Column(name = "tracking_id") private int trackingId;
    @Column(name = "order_id") private String orderId;
    @ElementCollection @Column private List<String> items = new ArrayList<String>();
    @Column(name = "item_id") private int itemId;
    @Column private int status;
    @Column(name = "sold_by_seller") private int soldBySeller;
    @Column private int amount;
    @Column(name = "currency_id") private int currencyId;
    @Column private Boolean cancel;
    @Column(name = "cancel_date") private Date cancelDate;
    @Column(name = "sold_date") private Date soldDate;
    @Column(name = "ship_by_date") private Date shipByDate;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP) @CreatedDate private Date createdAt = new Date();
}