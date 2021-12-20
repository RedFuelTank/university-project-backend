package com.example.main.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.users")
@Getter
@Setter
public class UsersConfig {
    private String userName;
    private String userPassword;
    private String adminName;
    private String adminPassword;
}
