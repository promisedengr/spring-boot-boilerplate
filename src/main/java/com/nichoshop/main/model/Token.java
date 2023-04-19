package com.nichoshop.main.model;

import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "tokens")
public class Token {
    @Id @Column private Long id;
    @Column private String username;
    @Column private String hash;
    @Column(name = "hash_session") private String hashSession;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP) @CreatedDate private Date createdAt = new Date();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    public String getHashSession() {
        return hashSession;
    }
    public void setHashSession(String hashSession) {
        this.hashSession = hashSession;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
