package com.nichoshop.main.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;

/**
 * Created by Nursultan on 08/11/22.
 */
@Entity
@Table(name = "notices")
public class Notice {

    @Id @Column private Long id;
    @Column private int from;
    @Column private int to;
    @Column(name = "notice_type") private int noticeType;
    @Column(name = "cancel_order_id") private int cancelOrderId;
    @Column(name = "created_at", nullable = false, updatable = false) @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate private Date createdAt = new Date();

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
     * @return int return the from
     */
    public int getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(int from) {
        this.from = from;
    }

    /**
     * @return int return the to
     */
    public int getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(int to) {
        this.to = to;
    }

    /**
     * @return int return the noticeType
     */
    public int getNoticeType() {
        return noticeType;
    }

    /**
     * @param noticeType the noticeType to set
     */
    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
    }

    /**
     * @return int return the cancelOrderId
     */
    public int getCancelOrderId() {
        return cancelOrderId;
    }

    /**
     * @param cancelOrderId the cancelOrderId to set
     */
    public void setCancelOrderId(int cancelOrderId) {
        this.cancelOrderId = cancelOrderId;
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
