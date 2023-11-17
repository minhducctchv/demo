package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "otp")
@Table
@Getter
@Setter
@Builder
public class OtpEntity extends BaseEntity{
    @Builder.Default
    private String otp = UUID.randomUUID().toString();

    @Builder.Default
    private LocalDateTime expired = LocalDateTime.now().plusMinutes(30); // thoi han otp la 30p
}
