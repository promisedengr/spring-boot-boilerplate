package com.nichoshop.main.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SellFilterRequest {
    private int status = 0;
    private int duration = 0;
    private int searchKey = 0;
    private String searchWord = "";
    private int sort = 0;
}
