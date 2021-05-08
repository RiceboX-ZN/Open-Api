package com.school.good.dto;

import com.school.common.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.util.Date;

@Getter
@Setter
public class UserDomainDto extends BaseDomain {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    @Transient
    private String password;
    /**
     * 账户没有过期
     */
    private boolean accountNonExpired;
    /**
     * 账户没有被锁
     */
    private boolean accountNonLocked;
    /**
     * 凭证没有过期
     */
    private boolean credentialsNonExpired;
    /**
     * 账户可以被使用
     */
    private boolean enabled;

    /**
     * 账户ID
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 国际冠码
     */
    private String idd;
    /**
     * 性别
     */
    private String gender;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 学校
     */
    private String schoolName;
    /**
     * 账户过期时间
     */
    private Date accountExpiredTime;
    /**
     * 账户被锁时间
     */
    private Date accountLockedTime;
    /**
     * 账户凭证过期时间
     */
    private Date credentialsExpiredTime;
    /**
     * 是管理员
     */
    private boolean admin;
    /**
     * 是否启用
     */
    private boolean able;
    /**
     * 注册类型
     */
    private String userType;
    /**
     * 支付宝用户ID
     */
    private String aliPayUserId;
    /**
     * 微信用户ID
     */
    private String weCharUserId;

    public UserDomainDto() {
    }
}
