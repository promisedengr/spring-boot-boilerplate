package com.nichoshop.main.model;

import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.mindrot.jbcrypt.BCrypt;
import lombok.Data;

/**
 * Created by Nursultan 08/10/22.
 */
@Data
@Entity
@Table(name = "customer_supports")
public class CustomerSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private Long adminId;
    @Column(nullable = true)
    private String username;
    @Column
    private String fname;
    @Column
    private String lname;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private int depart;
    @Column
    private int subDepart;
    @Column
    private String timezone;
    @Column
    private Boolean contact;
    @Column
    private int monToFri;
    @Column
    private int saturday;
    @Column
    private int sunday;
    @Column
    private int currencyId;
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt = new Date();

    public void setPassword(String password) {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        this.password = hash;
    }
}
