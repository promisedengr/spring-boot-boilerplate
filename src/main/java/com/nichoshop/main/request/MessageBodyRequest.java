package com.nichoshop.main.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MessageBodyRequest {
    private Long toId;
    private String message = "";
    private String reason;
    
}
