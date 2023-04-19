package com.nichoshop.main.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

/**
 * Created by Nursultan on 08/11/22.
 */
@Data
@Entity
@Table(name = "password_resets")
public class PasswordReset {

    @Id @Column private Long id;
    @Column(name = "user_id") private int userId;
    @Column private String type;
    @Column(name = "is_active") private Boolean isActive;
    @Column(nullable = true) private String hash;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate private Date createdAt = new Date();

}
