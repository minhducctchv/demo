package com.example.demo.service.impl;

import com.example.demo.constant.ActiveStatus;
import com.example.demo.constant.Role;
import com.example.demo.dto.data.Email;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.OtpEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.system_bean.EmailService;
import com.example.demo.util.ModelMapperUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Value("${backend.domain}")
    private String backendDomain;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public UserEntity createUser(UserRequest userRequest, Role... roles) {
        UserEntity user = ModelMapperUtil.map(userRequest, UserEntity.class);
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        if (roles.length < 1) {
            user.setRoles(List.of(Role.USER));
        } else {
            user.setRoles(Arrays.asList(roles));
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserResponse signIn(UserRequest userRequest) throws MessagingException {
        // tao user
        UserEntity user = this.createUser(userRequest);

        // gui mail
        user.setOtp(new OtpEntity());

        userRepository.save(user);

        String url = backendDomain + "/public/verify-account?otp=" + user.getOtp().getOtp();

        Context context = new Context();
        context.setVariable("confirmationUrl", url);
        String template = springTemplateEngine.process("confirm-email", context);

        emailService.sendMail(
                Email.builder()
                        .to(user.getEmail())
                        .subject("Confirm email")
                        .text(template)
                        .build()
        );
        return ModelMapperUtil.map(user, UserResponse.class);
    }

    @Override
    @Transactional
    public void confirmEmail(String otp) {
        UserEntity user = userRepository.findByOtp_Otp(otp).orElseThrow(
                () -> new CustomException("User not found")
        );
        if (Objects.isNull(user.getOtp())) {
            throw new CustomException("Otp not found");
        }

        if (user.getOtp().getExpired().compareTo(LocalDateTime.now()) < 0) {
            throw new CustomException("Otp was expired");
        }

        user.setIsEnable(true);
        user.setOtp(null);
        userRepository.save(user);
    }
}
