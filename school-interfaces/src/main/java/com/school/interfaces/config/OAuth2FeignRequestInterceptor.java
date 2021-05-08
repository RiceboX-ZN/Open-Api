package com.school.interfaces.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class OAuth2FeignRequestInterceptor  implements RequestInterceptor {

    public static final String AUTHENTICATION = "Authorization";
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authorization = request.getHeader(OAuth2FeignRequestInterceptor.AUTHENTICATION);
        requestTemplate.header(OAuth2FeignRequestInterceptor.AUTHENTICATION, authorization);
    }

}
