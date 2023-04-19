package com.nichoshop.main.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column
   private Long id;
   @Column
   private String username;
   @Column
   protected String password;
   @Column
   private String fname;
   @Column
   private String lname;
   @Column
   private String email;
   @Column(name = "email_confirmed")
   private Boolean emailConfirmed = false;
   @Column(name = "address_id", nullable = true)
   private Long addressId;
   @Column(name = "from_address_id", nullable = true)
   private Long fromAddressId;
   @Column(name = "to_address_id", nullable = true)
   private Long toAddressId;
   @Column(name = "return_address_id", nullable = true)
   private Long returnAddressId;
   @Column(name = "payment_address_id", nullable = true)
   private Integer paymentAddressId;
   @Column(nullable = true)
   private String phone;
   @Column(name = "phone_confirmed")
   private Boolean phoneConfirmed = false;
   @Column(name = "account_type")
   private Integer accountType;
   @Column(name = "business_id", nullable = true)
   private Integer businessId;
   @Column
   private Boolean locked = false;
   @Column
   private Boolean deleted = false;
   @Column(name = "deleted_date", nullable = true)
   private Long deletedDate;
   @Column(name = "created_at", nullable = false, updatable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @CreatedDate
   private Date createdAt = new Date();

   public User(String username, String password, String email, String fname, String lname, int accountType) {
      // super();
      this.username = username;
      // this.password = password;
      this.setPassword(password);
      this.email = email;
      this.fname = fname;
      this.lname = lname;
      this.accountType = accountType;
   }

}