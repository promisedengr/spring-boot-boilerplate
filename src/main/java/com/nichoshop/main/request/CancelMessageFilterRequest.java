package com.nichoshop.main.request;

import lombok.Data;

@Data
public class CancelMessageFilterRequest {
    private int status;
    private int duration;
    private int searchKey;    
}
