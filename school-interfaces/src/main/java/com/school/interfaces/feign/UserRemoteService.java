package com.school.interfaces.feign;

import com.school.interfaces.dto.UserDomainDto;
import com.school.interfaces.feign.fallback.UserRemoteServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "school-oauth", fallbackFactory = UserRemoteServiceImpl.class)
public interface UserRemoteService {

    /**
     * 用户当前登录用户
     *
     * @return
     */
    @PostMapping("/users/current-userInfo-form-login")
    UserDomainDto currentUserInfoFormLogin();
}
