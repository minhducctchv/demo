package com.example.demo.dto.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Email {
    @Builder.Default
    private String from = "CSKH";
    private String to;
    private String subject;
    private String[] bcc;
    private String[] cc;
    private String replyTo;
    private String text;
}
