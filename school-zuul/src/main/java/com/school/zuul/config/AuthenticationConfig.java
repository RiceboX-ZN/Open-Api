package com.school.zuul.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AuthenticationConfigurationProperties.class)
public class AuthenticationConfig {
}
