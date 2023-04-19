package com.nichoshop.main.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateCreditCardRequest {
    private String holder;
    private int cardType;
    private Long number;
    private int month;
    private int year;
    private String code;
    private int addressId;
    private Boolean status;

    
}
