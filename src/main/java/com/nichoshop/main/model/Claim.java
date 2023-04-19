package com.nichoshop.main.model;

import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "claims")
public class Claim {
    @Id @Column private Long id;
    @Column(name = "order_id") private int orderId;
    @ElementCollection @Column(name = "item_ids") private List<Integer> itemIds = new ArrayList<Integer>();
    @ElementCollection @Column private List<Integer> qtys = new ArrayList<Integer>();
    @Column private int status = 0;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP) @CreatedDate
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
     * @return int return the orderId
     */
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public List<Integer> getItemIds() {
        return itemIds;
    }
    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    /**
     * @return List<Integer> return the qtys
     */
    public List<Integer> getQtys() {
        return qtys;
    }

    /**
     * @param qtys the qtys to set
     */
    public void setQtys(List<Integer> qtys) {
        this.qtys = qtys;
    }

    /**
     * @return int return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
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
