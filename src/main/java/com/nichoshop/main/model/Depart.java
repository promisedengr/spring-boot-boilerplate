package com.nichoshop.main.model;

import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;

/**
 * Created by Nursultan 08/10/22.
 */
@Entity
@Table(name = "departs")
public class Depart {

    @Id @Column private Long id;
    @Column private String name;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate private Date createdAt = new Date();

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
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
