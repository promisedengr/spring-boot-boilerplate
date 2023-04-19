package com.nichoshop.main.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChangePasswordRequest {
    private String password;
    private String confirm;
    private String username;
    private String token;
    
}
