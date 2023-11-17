package com.example.demo.entity;

import com.example.demo.constant.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
@Table
@Getter
@Setter
@Builder
public class UserEntity extends BaseEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;
    private String roles;

    public List<Role> getRoles() {
        String[] roleStrings = this.roles.split(",");
        return Arrays.stream(roleStrings).map(Role::valueOf).collect(Collectors.toList());
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles.stream().map(Enum::toString).collect(Collectors.joining(","));
    }

    private LocalDateTime expired;

    @Builder.Default
    private Boolean isEnable = false;

    @Builder.Default
    private LocalDateTime expiredPassword = LocalDateTime.now().plusMonths(3); // 3 thang la phai doi pass

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "otp_id", referencedColumnName = "id")
    private OtpEntity otp;
}
