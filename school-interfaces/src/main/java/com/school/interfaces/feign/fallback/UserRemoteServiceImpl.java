package com.school.interfaces.feign.fallback;

import com.school.interfaces.dto.UserDomainDto;
import com.school.interfaces.feign.UserRemoteService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserRemoteServiceImpl implements FallbackFactory<UserRemoteService> {

    @Override
    public UserRemoteService create(Throwable throwable) {
        return new UserRemoteService() {
            @Override
            public UserDomainDto currentUserInfoFormLogin() {
                log.error("获取当前登录用户信息失败 : {}", throwable.getMessage());
                return null;
            }
        };
    }
}
