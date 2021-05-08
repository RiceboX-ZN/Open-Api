package com.school.oauth2.endpoint.service;

import com.school.oauth2.endpoint.domain.UserInfo;

public interface UserService {
    /**
     * 新建用户，不包括角色、权限信息
     * @param user
     * @return
     */
    UserInfo createUser(UserInfo user);

    /**
     * 更新角色信息，包括密码，但不包括角色、权限信息
     * @param user
     * @return
     */
    UserInfo updateUserInfo(UserInfo user);

    /**
     * 删除用户信息
     * @param user
     * @return
     */
    UserInfo deleteUser(UserInfo user);

    /**
     * 查询用户详细信息，包括角色和权限
     * @param user
     * @return
     */
    UserInfo queryUserDetails(UserInfo user);
}
