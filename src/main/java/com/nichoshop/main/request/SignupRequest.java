package com.nichoshop.main.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SignupRequest {
    private String email;
	private String grecaptcha;
	private String name;
	private int accountType;
	private String password;
}
