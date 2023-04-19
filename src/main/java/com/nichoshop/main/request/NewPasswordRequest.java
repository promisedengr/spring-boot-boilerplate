package com.nichoshop.main.request;

import lombok.Data;

@Data
public class NewPasswordRequest {
    private String curPass;
    private String newPass;
    private String username;
}
