package com.nichoshop.main.request;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateOrderRequest {
    private Long sellerId;
    private int payment;
    private Long delivery;
    private Long trackingId;
    private String orderId;
    private List<String> items;
    private int status;
}
