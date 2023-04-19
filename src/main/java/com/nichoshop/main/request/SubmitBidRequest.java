package com.nichoshop.main.request;

import lombok.Data;

@Data
public class SubmitBidRequest {
    private Long itemId;
    private Long userId;
    private int amount;
    private Long currencyId;
}
