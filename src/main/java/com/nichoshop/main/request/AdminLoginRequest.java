package com.nichoshop.main.request;

import lombok.Data;

@Data
public class AdminLoginRequest {
    String login;
    String password;
    Boolean rememberMe;
    String recaptcha;
}
