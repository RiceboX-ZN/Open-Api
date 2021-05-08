package com.school.good.feign;

import com.school.good.dto.UserDomainDto;
import com.school.good.feign.fallback.UserRemoteServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "school-oauth", fallbackFactory = UserRemoteServiceImpl.class)
public interface UserRemoteService {

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @GetMapping("/users/current-userInfo-form-login")
    UserDomainDto currentUserInfoFormLogin();

}
