package com.example.demo.service.system_bean;

import com.example.demo.dto.data.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendMail(Email email) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(email.getFrom());
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        if (Objects.nonNull(email.getBcc()) && email.getBcc().length > 0) helper.setBcc(email.getBcc());
        if (Objects.nonNull(email.getCc()) && email.getCc().length > 0) helper.setCc(email.getCc());
        if (Strings.isNotEmpty(email.getReplyTo())) helper.setReplyTo(email.getReplyTo());
        helper.setText(email.getText(), true);

        emailSender.send(message);
    }
}
