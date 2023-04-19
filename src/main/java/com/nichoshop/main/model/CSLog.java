package com.nichoshop.main.model;

import javax.persistence.*;

/**
 * Created by Nursultan 08/10/22.
 */
@Entity
@Table(name = "cs_logs")
public class CSLog {

    @Id @Column private Long id;

    @Column private int csId;

    @Column private Long from;

    @Column(nullable = true) private Long to;

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
     * @return int return the csId
     */
    public int getCsId() {
        return csId;
    }

    /**
     * @param csId the csId to set
     */
    public void setCsId(int csId) {
        this.csId = csId;
    }

    /**
     * @return Long return the from
     */
    public Long getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(Long from) {
        this.from = from;
    }

    /**
     * @return Long return the to
     */
    public Long getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(Long to) {
        this.to = to;
    }

}
