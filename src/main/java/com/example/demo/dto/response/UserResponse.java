package com.example.demo.dto.response;

import com.example.demo.constant.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private List<Role> roles;
}
