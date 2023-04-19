package com.nichoshop.main.request;

import lombok.Data;

@Data
public class AddCartRequest {
    private Long itemId;
    private Long userId;
    private int quantity;
}
