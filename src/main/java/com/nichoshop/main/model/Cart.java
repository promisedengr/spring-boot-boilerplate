package com.nichoshop.main.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @Column
    private Long id;
    @Column
    private Long userId;
    @Column
    private Long itemId;
    @Column
    private int qty;
    @Column
    private Boolean flag = false;

}
