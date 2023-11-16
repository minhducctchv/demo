package com.example.demo.service.impl;

import com.example.demo.util.ModelMapperUtil;
import com.example.demo.constant.Role;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserEntity createUser(UserRequest userRequest, Role ...roles) {
        UserEntity user = ModelMapperUtil.map(userRequest, UserEntity.class);
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        if (roles.length <1) {
            user.setRoles(List.of(Role.USER));
        } else {
            user.setRoles(Arrays.asList(roles));
        }
        return userRepository.save(user);
    }

    @Override
    public UserResponse signIn(UserRequest userRequest) {
        // tao user
        UserEntity user = this.createUser(userRequest);

        // gui mail
        return null;
    }
}
