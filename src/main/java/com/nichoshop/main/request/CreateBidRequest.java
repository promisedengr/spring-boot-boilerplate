package com.nichoshop.main.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateBidRequest {
    private int itemId;
    private int price;
    private int currencyId;
    
}
