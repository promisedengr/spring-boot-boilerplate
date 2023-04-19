package com.nichoshop.main.request;

import lombok.Data;

@Data
public class SellerOrderRequest {
    private int status = 0;
    private int duration = 0;
    private int searchKey = 0;
    private String searchWord = "";
    private int sort = 0; // 0: soldDate asc, 1: soldDate desc, 2: shipByDate asc, 3: shipByDate desc
}
