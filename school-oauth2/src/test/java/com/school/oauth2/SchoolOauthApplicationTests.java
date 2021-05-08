package com.school.oauth2;

import com.school.oauth2.endpoint.domain.Permission;
import com.school.oauth2.endpoint.domain.Role;
import com.school.oauth2.endpoint.domain.UserInfo;
import com.school.oauth2.endpoint.mapper.PermissionMapper;
import com.school.oauth2.endpoint.mapper.RoleMapper;
import com.school.oauth2.endpoint.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolOath2Application.class)
public class SchoolOauthApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    void contextLoads() {
        System.out.println(ZonedDateTime.now(ZoneId.systemDefault()));
    }
    
    @Test
    public void test() {
        UserInfo userInfo = userMapper.selectByUsername("admin");
        userInfo.getRoleList().forEach(role -> {
            System.out.println(role.getRoleName());
        });
    }

    @Test
    public void testPermissionMapper() {
        List<Permission> permissions = permissionMapper.selectPermissionByRoleId(3L);
        permissions.forEach(permission -> {
            System.out.println(permission.getRequestUrl());
        });
    }

    @Test
    public void testRoleMapper() {
        List<Role> roles = roleMapper.selectRoleByPermissionId(2L);
        roles.forEach(role -> {
            System.out.println(role.getRoleName());
        });
    }

    @Test
    public void addAcount(){
        //账户：897229594
        String s = "897229594op";
        String encode = passwordEncoder.encode(s);
        System.out.println(encode);

    }

    @Test
    public void tests(){
        System.out.println("name: zhangshan");
        System.out.println("score: 95");
    }
}
