package com.example.demo.dto.data;

import com.example.demo.constant.ActiveStatus;
import com.example.demo.entity.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class CustomUserDetail implements UserDetails {
    private UserEntity user;

    public CustomUserDetail(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(CustomGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        if (Objects.nonNull(user.getExpired())) {
            return user.getExpired().compareTo(LocalDateTime.now()) > 0;
        }
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus().equals(ActiveStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // mat khau da het han hay chua
        return user.getExpiredPassword().compareTo(LocalDateTime.now()) > 0;
    }

    @Override
    public boolean isEnabled() {
        // tai khoan da duoc kich hoat hay chua
        return user.getIsEnable();
    }
}
