package com.example.demo.dto.data;

import com.example.demo.constant.Role;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
    private final Role role;

    @Override
    public String getAuthority() {
        return role.toString();
    }

    public CustomGrantedAuthority(Role role) {
        this.role = role;
    }
}
