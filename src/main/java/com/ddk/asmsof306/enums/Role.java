package com.ddk.asmsof306.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Role implements GrantedAuthority{
    USER,
    ADMIN,
    MANAGER;


    @Override
    public String getAuthority() {
        return name();
    }
}
