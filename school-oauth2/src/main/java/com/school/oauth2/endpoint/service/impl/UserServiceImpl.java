package com.school.oauth2.endpoint.service.impl;

import com.google.common.collect.Lists;
import com.school.common.exception.CommonException;
import com.school.oauth2.endpoint.constant.RoleName;
import com.school.oauth2.endpoint.domain.Role;
import com.school.oauth2.endpoint.domain.UserInfo;
import com.school.oauth2.endpoint.domain.UserRole;
import com.school.oauth2.endpoint.mapper.RoleMapper;
import com.school.oauth2.endpoint.mapper.UserMapper;
import com.school.oauth2.endpoint.mapper.UserRoleMapper;
import com.school.oauth2.endpoint.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserInfo createUser(UserInfo user) {
        //校验用户名是否唯一
        UserInfo temp = new UserInfo();
        temp.setUsername(user.getUsername());
        UserInfo userInfo = this.userMapper.selectOne(temp);
        if (userInfo != null) {
            throw new CommonException("账户名重复：" + user.getUsername());
        }
        if (user.getCreationDataTime() == null) {
            //系统时间
            user.setCreationDataTime(new Date());
        }
        //给密码加密
        user.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));
        this.userMapper.insertSelective(user);
        //给用户添加User角色
        user = this.userMapper.selectOne(temp);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        Role role = new Role();
        role.setRoleName(RoleName.USER.name());
        role = this.roleMapper.selectRoleByRoleName(role.getRoleName());
        userRole.setRoleId(role.getRoleId());
        this.userRoleMapper.insertSelective(userRole);
        user.setRoleList(Lists.newArrayList(role));
        return user;
    }

    @Override
    public UserInfo updateUserInfo(UserInfo user) {
        //修改了密码
        String password = user.getPassword();
        if (StringUtils.isNotEmpty(password)) {
            user.setEncryptedPassword(passwordEncoder.encode(password));
        }
        userMapper.updateByPrimaryKeySelective(user);
        return userMapper.selectByPrimaryKey(user.getUserId());
    }

    @Override
    public UserInfo deleteUser(UserInfo user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getUserId());
        userInfo.setAble(false);
        //删除与role的关联
        UserRole userRole = new UserRole().setUserId(user.getUserId());
        userRoleMapper.delete(userRole);
        this.userMapper.updateByPrimaryKeySelective(userInfo);
        return user;
    }

    @Override
    public UserInfo queryUserDetails(UserInfo user) {
        return this.userMapper.queryUserDetailsByUserId(user.getUserId());
    }

}
