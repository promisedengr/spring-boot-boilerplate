package com.nichoshop.main.model;

import javax.persistence.*;

/**
 * Created by Nursultan on 08/10/22.
 */
@Entity
@Table(name = "conditions")
public class Condition {

    @Id @Column private Long id;
    @Column private String name;

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

}
