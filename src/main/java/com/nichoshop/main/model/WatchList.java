package com.nichoshop.main.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

/**
 * Created by Nursultan 08/11/22.
 */
@Data
@Entity
@Table(name = "watch_lists")
public class WatchList {

    @Id
    @Column
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt = new Date();

}
