package com.school.oauth2.config;

import com.school.oauth2.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * 认证服务配置（第一个被访问）
 *  访问地址http://localhost:10012/oauth/authorize?client_id=client&response_type=code,然后输入用户名和密码以及验证码验证
 *  或者 http://localhost:10012/oauth/authorize?response_type=code&client_id=client&scope=global&redirect_uri=http://www.baidu.com
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {


    public static class RandomKeyGenerator extends DefaultAuthenticationKeyGenerator {
        @Override
        public String extractKey(OAuth2Authentication authentication) {
            return UUID.randomUUID().toString();
        }
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private DataSource dataSource;
    /**
     * 认证端点
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .authorizationCodeServices(new JdbcAuthorizationCodeServices(this.dataSource))
                .tokenStore(tokenStore())
                .userDetailsService(userDetailsService())
                .setClientDetailsService(jdbcClientDetailsService());

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    @Bean
    public ClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(this.dataSource);
    }

    @Bean
    public TokenStore tokenStore() {
        JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(this.dataSource);
        jdbcTokenStore.setAuthenticationKeyGenerator(new RandomKeyGenerator());
        return jdbcTokenStore;
    }

    /**
     * 配置认证端点的安全性
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //token_key端点、check_token端点都可以访问，密码加密的时候使用加密器
        security.passwordEncoder(passwordEncoder)
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    /**
     * 客户端详细配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //设置一个全局客户端，大家都通过这个来认证以及访问资源，token的有效期为30分钟，30分钟刷新一次
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * /oauth/authorize     授权端点
     * /oauth/token         令牌端点
     * /oauth/confirm-access用户确认授权提交端点
     * /auth/error          授权服务错误信息端点
     * /auth/check_token    用户资源服务访问的令牌解析端点
     * /oauth/token_key     提供公有密钥的端点，如果你使用jwt令牌的话
     */
}

