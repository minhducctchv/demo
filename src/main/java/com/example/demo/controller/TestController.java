package com.example.demo.controller;

import com.example.demo.dto.data.Email;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.service.UserService;
import com.example.demo.service.system_bean.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @GetMapping("/send-mail")
    public String sendMail(String content) throws MessagingException {
        Email email = Email.builder()
                .subject("Test mail 01")
                .to("sieunhanbay1997@gmail.com")
                .text("xin chao").build();
        emailService.sendMail(email);
        return "success";
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody UserRequest userRequest) throws MessagingException {
        userService.signIn(userRequest);
        return "success";
    }
}
