package com.school.interfaces.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedMethods("GET", "PUT", "DELETE", "POST", "PATCH", "HEAD", "TRACE")
                .allowedOrigins("*")
                .allowCredentials(true)
                .maxAge(3600);

    }
}
