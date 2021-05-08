package com.school.oauth2.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web安全配置（第三个被访问）
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 配置安全性
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
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
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and().csrf().disable();

    }
}
