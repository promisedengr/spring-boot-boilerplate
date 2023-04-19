package com.nichoshop.main.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @Column
    private Long id;
    @Column
    private Long reportType;
    @Column
    private Long specificType;
    @Column
    private Long detailType;
    @Column
    private Long itemId;
    @Column
    private String content;
    @Column
    private Long userId;
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt = new Date();
}
