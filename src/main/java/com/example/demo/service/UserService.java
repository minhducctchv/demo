package com.example.demo.service;

import com.example.demo.constant.Role;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.UserEntity;
import jakarta.mail.MessagingException;

public interface UserService {
    UserEntity createUser(UserRequest userRequest, Role ...role);

    UserResponse signIn(UserRequest userRequest) throws MessagingException;

    void confirmEmail(String otp);
}
