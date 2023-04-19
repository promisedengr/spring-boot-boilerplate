package com.nichoshop.main.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;

@Table(name = "message_folders")
public class MessageFolder {

    @Id @Column private Long id;
    @Column private String title;
    @Column(name = "user_id") private int userId;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate private Date createdAt = new Date();

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return int return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return Date return the created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param created_at the created_at to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
