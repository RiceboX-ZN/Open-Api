package com.school.oauth2.endpoint.contorller;

import com.school.common.exception.CommonException;
import com.school.oauth2.endpoint.domain.Role;
import com.school.oauth2.endpoint.inter.Admin;
import com.school.oauth2.endpoint.inter.Authentication;
import com.school.oauth2.endpoint.inter.User;
import com.school.oauth2.endpoint.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Api(value = "角色接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all-roles")
    @ApiOperation(value = "查询所有角色信息，不包括权限信息")
    @Authentication(group = {Admin.class, User.class})
    public ResponseEntity<List<Role>> queryAll() {
        List<Role> roles = this.roleService.queryAll();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/role-details")
    @ApiOperation(value = "查询角色详情，包含权限信息")
    @Authentication(group = {Admin.class})
    public ResponseEntity<Role> queryRoleDetails(@Validated @RequestBody Role role) {
        if (!StringUtils.hasText(role.getRoleName()) || role.getRoleId() == null) {
            throw new CommonException("角色姓名不能存在空格号：" + role.getRoleName() + "，角色ID不能为空：" + role.getRoleId());
        }
        return ResponseEntity.ok(this.roleService.queryRoleDetails(role));
    }

}
