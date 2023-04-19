package com.nichoshop.main.model;

import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "sub_depars")
public class SubDepart {

    @Id @Column private Long id;
    @Column private String name;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP) @CreatedDate private Date createdAt = new Date();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
