package com.nichoshop.main.model;

import javax.persistence.*;

@Entity
@Table(name = "businesses")
public class Business {

    @Id @Column private Long id;
    @Column(name = "user_id") private int userId;
    @Column private String name;
    @Column(name = "address_id", nullable = true) private int addressId;
    @Column(name = "vat_country") private String vatCountry;
    @Column(name = "vat_number") private String vatNumber;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    public String getVatCountry() {
        return vatCountry;
    }
    public void setVatCountry(String vatCountry) {
        this.vatCountry = vatCountry;
    }
    public String getVatNumber() {
        return vatNumber;
    }
    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

}
