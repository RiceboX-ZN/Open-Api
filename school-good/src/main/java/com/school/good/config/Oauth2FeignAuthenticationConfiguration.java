package com.school.good.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class Oauth2FeignAuthenticationConfiguration implements RequestInterceptor {

    public static final String AUTHENTICATION = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String authentication = request.getHeader(Oauth2FeignAuthenticationConfiguration.AUTHENTICATION);
        requestTemplate.header(Oauth2FeignAuthenticationConfiguration.AUTHENTICATION, authentication);
    }
}
