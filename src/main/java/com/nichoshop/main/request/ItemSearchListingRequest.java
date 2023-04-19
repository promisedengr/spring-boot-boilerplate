package com.nichoshop.main.request;

import lombok.Data;

@Data
public class ItemSearchListingRequest {
    private String searchString;
    private Integer days;
    private Integer searchType; // 0: title search, 1: nsln search
    private Integer priceType;
    
}
