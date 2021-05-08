package com.school.oauth2.endpoint.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.common.domain.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Table(name = "oauth_user")
@ApiModel(value = "用户表")
@Data
public class UserInfo extends BaseDomain {

    @Id
    @NotNull(groups = {Update.class, Delete.class, Select.class})
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "用户主键，提供给其他表做外键,只读", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private Long userId;

    @Column(unique = true)
    @NotBlank
    @Length(max = 32)
    @ApiModelProperty(value = "用户名，必须唯一")
    private String username;

    @NotBlank
    @Length(max = 32)
    @ApiModelProperty(value = "昵称，可以重复")
    private String nickname;

    @JsonIgnore
    @Length(max = 128)
    @ApiModelProperty(value = "加密密码")
    private String encryptedPassword;

    @Length(max = 32)
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$"
    ,message = "手机号码格式错误")
    @ApiModelProperty(value = "用户手机号码")
    private String phone;

    @Email
    @Length(max = 128)
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @Length(max = 8)
    @ApiModelProperty(value = "国际冠码，默认 +86")
    private String idd;

    @Length(max = 16)
    @ApiModelProperty(value = "性别：男/女")
    private String gender;

    @Length(max = 1024)
    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @NotBlank
    @Length(max = 128)
    @ApiModelProperty(value = "所属学校")
    private String schoolName;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "账户过期时间")
    private Date accountExpiredTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "账户锁定时间")
    private Date accountLockedTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "凭证过期时间")
    private Date credentialsExpiredTime;

    @Column(name = "is_admin")
    @ApiModelProperty(value = "是否是管理员")
    private Boolean admin;

    @Column(name = "is_able")
    @ApiModelProperty(value = "账户是否启用")
    private Boolean able;

    @Length(max = 32)
    @NotBlank(groups = Insert.class)
    @ApiModelProperty(value = "用户注册平台：WEB(默认)/AliPay/WeChat")
    private String userType;

    @Length(max = 64)
    @ApiModelProperty(value = "微信用户识别ID")
    private String weChatUserId;

    @Length(max = 64)
    @ApiModelProperty(value = "支付宝用户识别ID")
    private String aliPayUserId;


    @Transient
    @NotBlank(groups = Insert.class)
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 一个用户只能对应一个角色（最高权限角色）
     */
    @Transient
    @ApiModelProperty(value = "用户角色集合：guest/user/admin")
    private List<Role> roleList;

    public UserInfo() {
    }

}
