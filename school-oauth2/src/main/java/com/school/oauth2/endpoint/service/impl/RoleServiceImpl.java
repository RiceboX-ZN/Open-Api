package com.school.oauth2.endpoint.service.impl;

import com.school.oauth2.endpoint.domain.Role;
import com.school.oauth2.endpoint.mapper.RoleMapper;
import com.school.oauth2.endpoint.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> queryAll() {

        //一定要这样写，不然有安全性错误
        return (ArrayList<Role>) roleMapper.selectAllById(null);
    }

    @Override
    public Role queryRoleDetails(Role role) {
        return roleMapper.selectRoleByRoleName(role.getRoleName());
    }
}
