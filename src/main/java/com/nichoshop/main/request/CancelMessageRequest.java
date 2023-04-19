package com.nichoshop.main.request;

import lombok.Data;

@Data
public class CancelMessageRequest {
    private int toUserId;
    private String message;
    private String reason;
}
