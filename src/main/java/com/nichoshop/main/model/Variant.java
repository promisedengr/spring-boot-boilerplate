package com.nichoshop.main.model;

import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "variants")
public class Variant {

    @Id @Column private Long id;
    @Column private int itemId;
    @Column private String title;
    @Column private String desc;
    @Column private String cond;
    @Column private int amount;
    @Column(name = "currency_id") private int currencyId;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate private Date createdAt = new Date();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getCond() {
        return cond;
    }
    public void setCond(String cond) {
        this.cond = cond;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getCurrencyId() {
        return currencyId;
    }
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
