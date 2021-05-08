package com.school.oauth2.endpoint.service;

import com.school.oauth2.endpoint.domain.Role;

import java.util.List;

public interface RoleService {

    /**
     * 查询所有角色信息（不包括权限信息）
     * @return
     */
    List<Role> queryAll();

    /**
     * 查询角色详细信息，包括权限详情
     * @param role
     * @return
     */
    Role queryRoleDetails(Role role);

}
