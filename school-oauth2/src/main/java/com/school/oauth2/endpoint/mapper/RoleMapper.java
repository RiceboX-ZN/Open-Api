package com.school.oauth2.endpoint.mapper;

import com.school.oauth2.endpoint.domain.Role;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface RoleMapper extends Mapper<Role> {

    /**
     * 根据用户Id,查询角色信息（没有包含权限信息）
     * @param userId
     * @return
     */
    List<Role> selectRoleByUserId(Long userId);

    /**
     * 根据权限Id,查询对应的所有角色信息
     * @param permissionId
     * @return
     */
    List<Role> selectRoleByPermissionId(Long permissionId);

    /**
     * 根据角色名称查询详情信息，包括权限信息
     * @param roleName
     * @return
     */
    Role selectRoleByRoleName(String roleName);

    /**
     * 根据ID，查询所有权限信息
     * @param roleId
     * @return
     */
    List<Role> selectAllById(Long roleId);

}
