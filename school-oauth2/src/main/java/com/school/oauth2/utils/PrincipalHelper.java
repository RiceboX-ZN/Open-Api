package com.school.oauth2.utils;

import com.school.common.exception.UserNotLoginException;
import com.school.oauth2.endpoint.constant.RoleName;
import com.school.oauth2.user.domain.UserDetailDomain;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Optional;

/**
 * 获取当前用户
 */
public class PrincipalHelper {

    private static final ThreadLocal<UserDetailDomain> userDetailDomainThreadLocal = new ThreadLocal<>();
    private static final UserDetailDomain ANONYMOUS = (new UserDetailDomain("username", "", Arrays.asList(new SimpleGrantedAuthority(RoleName.GUEST.name())))).setUserId(-1L).setNickname("anonymous");

    public PrincipalHelper() {

    }


    /**
     * 注入当前用户信息
     * @param userDetailDomain
     */
    public static void setUserDetail(UserDetailDomain userDetailDomain) {
        userDetailDomainThreadLocal.set(userDetailDomain);
    }

    /**
     * 获取当前用户信息，为空的话，则抛出异常
     * @return
     */
    public static UserDetailDomain getCurrentUserDetail() {
        UserDetailDomain principal =(UserDetailDomain) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userDetailDomainThreadLocal.set(principal);
        return (UserDetailDomain) Optional.ofNullable(principal).orElseThrow(UserNotLoginException::new);
    }
    /**
     * 如果当前登录用户信息为空，则放返回一个匿名的用户（GUEST权限 == 无权限）
     * @return
     */
    public static UserDetailDomain notNullUserDetail() {
        return (UserDetailDomain) Optional.ofNullable((UserDetailDomain) userDetailDomainThreadLocal.get()).orElse(ANONYMOUS);
    }

    /**
     * 清除当前用户信息
     */
    public static void remove() {
        userDetailDomainThreadLocal.remove();
    }
}
