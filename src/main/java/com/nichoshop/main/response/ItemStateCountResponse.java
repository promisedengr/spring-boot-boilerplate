package com.nichoshop.main.response;

import lombok.Data;

@Data
public class ItemStateCountResponse {
    private int activeCnt;
    private int outOfStockCnt;
    private int unSoldCnt;
}
