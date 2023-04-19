package com.nichoshop.main.response;

import lombok.Data;

@Data
public class LoginResponse {
    String result;
    String email;
    String username;
    String token;

}
