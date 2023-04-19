package com.nichoshop.main.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChangeAddressRequest {
    private String address1;
    private String address2;
    private String city;
    private String country;
    private String state;
    private String phone;
    private String zip;
    private String name;
}
