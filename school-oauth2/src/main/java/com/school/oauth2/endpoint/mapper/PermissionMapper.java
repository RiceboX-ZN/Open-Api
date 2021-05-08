package com.school.oauth2.endpoint.mapper;

import com.school.oauth2.endpoint.domain.Permission;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface PermissionMapper extends Mapper<Permission> {
    /**
     * 根据角色ID，查询对应的权限信息
     * @param roleId
     * @return
     */
    List<Permission> selectPermissionByRoleId(Long roleId);
}
