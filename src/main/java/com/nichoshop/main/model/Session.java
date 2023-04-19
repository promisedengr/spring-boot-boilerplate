package com.nichoshop.main.model;

import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @Column
    private Long id;

    @Column
    private String username;

    @Column
    private String hash;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt = new Date();

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
     * @return String return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return String return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
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
