package com.school.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务配置（第二个被访问）
 */
@Configuration
@EnableResourceServer
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/public/**",
                        "/oauth/public/**",
                        "/webjars/**",
                        "webjars/springfox-swagger-ui/**",
                        "webjars/springfox-swagger-ui",
                        "/configuration/**",
                        "/swagger-ui.html",
                        "/static/**",
                        "/v2/api-docs**",
                        "/swagger-resources/**",
                        "/druid/**",
                        "/oauth/**",
                        "/actuator/**",
                        "/csrf", "/login").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("global");
    }
}
