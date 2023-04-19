package com.nichoshop.main.request;

import lombok.Data;

@Data
public class CSCreateRequest {
    private String fname;
    private String lname;
    private String password;
    private String email;
    private String phone;
    private int depart;
    private int subDepart;
    private String timezone;
    private Boolean contact;
    private int monToFri;
    private int saturday;
    private int sunday;
    private int currencyId;
}
