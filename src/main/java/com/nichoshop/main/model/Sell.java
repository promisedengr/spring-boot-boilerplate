package com.nichoshop.main.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

@Data
@Entity
@Table(name = "sells")
public class Sell {

    @Id @Column private Long id;
    @Column(name = "buyer_id") private int buyerId;
    @Column(name = "item_id") private int itemId;
    @Column private int quantity;
    @Column private int price;
    @Column private Boolean paid;
    @Column private Boolean dispatched;
    @Column(name = "sold_time") private Long soldTime;
    @Column(name = "seller_id") private int sellerId;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate private Date createdAt = new Date();
}
