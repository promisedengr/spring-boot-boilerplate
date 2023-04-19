package com.nichoshop.main.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "cancel_orders")
public class CancelOrder {

    @Id @Column private Long id;
    @Column(name = "user_id") private int userId;
    @Column(name = "order_id") private int orderId;
    @Column(name = "reason_id") private int reasonId;
    @Column(nullable = true) private String comment;
    @ElementCollection @Column private List<String> items = new ArrayList<String>();
    @ElementCollection @Column private List<String> qtys = new ArrayList<String>();
    @Column private Boolean accepted = false;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate private Date createdAt = new Date();

}
