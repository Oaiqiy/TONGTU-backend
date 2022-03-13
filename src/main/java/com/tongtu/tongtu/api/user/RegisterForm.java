package com.tongtu.tongtu.api.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tongtu.tongtu.domain.User;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterForm {
    private String username;
    private String password;
    private String email;
    private String uri;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), email);
    }
}