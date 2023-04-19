package com.nichoshop.main.model;

import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
@Table(name = "feedbacks")
public class FeedBack {

    @Id
    @Column
    private Long id;
    @Column
    private Long itemId;
    @Column
    private Long userId;
    @Column
    private String content;

    @Column
    private int rate;
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt = new Date();

}
