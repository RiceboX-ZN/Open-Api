package com.school.oauth2.endpoint.contorller;

import com.school.common.exception.CommonException;
import com.school.oauth2.endpoint.domain.UserInfo;
import com.school.oauth2.endpoint.inter.Admin;
import com.school.oauth2.endpoint.inter.Authentication;
import com.school.oauth2.endpoint.inter.User;
import com.school.oauth2.endpoint.service.UserService;
import com.school.oauth2.user.domain.UserDetailDomain;
import com.school.oauth2.utils.PrincipalHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@Api(value = "用户接口")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    @ApiOperation(value = "创建用户")
    @Authentication(group = {Admin.class, User.class})
    public ResponseEntity<UserInfo> createUser(@Validated @RequestBody UserInfo user) {
        String password = user.getPassword();
        if (!StringUtils.hasText(password) || StringUtils.isEmpty(password)) {
            throw new CommonException("密码存在空格或者为空" + user.getPassword());
        }
        return ResponseEntity.ok(this.userService.createUser(user));
    }

    @PostMapping("/current-userInfo-form-login")
    @ApiOperation(value = "获取当前登录用户信息")
    @Authentication(group = {Admin.class,User.class})
    public ResponseEntity<UserDetailDomain> currentUserInfoFormLogin() {
        UserDetailDomain domain = PrincipalHelper.getCurrentUserDetail();
        PrincipalHelper.remove();
        return ResponseEntity.ok(domain);
    }

    @PostMapping("/update-userInfo")
    @ApiOperation(value = "修改用户个人信息，包括修改密码,但不包括修改角色和权限信息")
    public ResponseEntity<UserInfo> updateUserInfo(@Validated @RequestBody UserInfo user) {
        user.setRoleList(null);
        user.setEncryptedPassword(null);
        UserInfo u = this.userService.updateUserInfo(user);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/delete-user")
    @ApiOperation(value = "删除用户")
    @Authentication(group = {User.class, Admin.class})
    public ResponseEntity<UserInfo> deleteUser(@Validated @RequestBody UserInfo user) {
        UserInfo u = this.userService.deleteUser(user);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/query-userInfo-details")
    @ApiOperation(value = "查询用户详情信息，包括角色和权限信息")
    @Authentication(group = {Admin.class})
    public ResponseEntity<UserInfo> queryUserDetails(@Validated @RequestBody UserInfo user) {
        UserInfo u = this.userService.queryUserDetails(user);
        return ResponseEntity.ok(u);
    }

    @GetMapping("/details")
    @ApiIgnore
    public ResponseEntity<UserDetailDomain> queryDetails(Principal principal) {
        if (principal instanceof OAuth2Authentication) {
            org.springframework.security.core.Authentication userAuthentication = ((OAuth2Authentication) principal).getUserAuthentication();
            if (userAuthentication != null && userAuthentication instanceof UserDetailDomain) {
                return ResponseEntity.ok((UserDetailDomain) userAuthentication.getPrincipal());
            }
        }
        //返回204
        return ResponseEntity.noContent().build();
    }

}
