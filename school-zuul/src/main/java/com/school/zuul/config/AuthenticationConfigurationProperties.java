package com.school.zuul.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = AuthenticationConfigurationProperties.PREFIX)
public class AuthenticationConfigurationProperties {

    public static final String PREFIX = "authentication";

    /**
     * 白名单
     */
    private List<String> skipPathPrefix;

    /**
     * 是否启用权限
     */
    private Boolean enable = true;

    /**
     * 用户详情请求地址
     */
    private String detailRequestUrl = "http://localhost:10012/users/details";


}
