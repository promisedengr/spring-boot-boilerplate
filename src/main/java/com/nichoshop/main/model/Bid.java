package com.nichoshop.main.model;

import lombok.Data;
import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;

@Data
@Entity
@Table(name = "bids")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private Long itemId;
    @Column
    private Long userId;
    @Column
    private int amount;
    @Column
    private Long currencyId; // currency id
    @Column
    private Boolean status = true; // true: Active false: Cancelled
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt = new Date();
}
