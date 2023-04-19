package com.nichoshop.main.security;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEum implements GrantedAuthority {
    ROLE_USER, ROLE_CS, ROLE_ADMIN;

    public String getAuthority() {
        return name();
    }

}
