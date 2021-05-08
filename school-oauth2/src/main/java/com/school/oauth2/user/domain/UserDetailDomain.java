package com.school.oauth2.user.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

public class UserDetailDomain extends User {
    private Long userId;
    private String nickname;
    private String phone;
    private String email;
    private String idd;
    private String gender;
    private String avatar;
    private String schoolName;
    private Date accountExpiredTime;
    private Date accountLockedTime;
    private Date credentialsExpiredTime;
    private boolean admin;
    private boolean able;
    private String userType;
    private String aliPayUserId;
    private String weCharUserId;

    public UserDetailDomain(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountExpiredTime == null || accountExpiredTime.after(new Date());
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountLockedTime == null || accountLockedTime.after(new Date());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsExpiredTime == null || credentialsExpiredTime.after(new Date());
    }

    @Override
    public boolean isEnabled() {
        return this.able;
    }


    public String getNickname() {
        return nickname;
    }

    public UserDetailDomain setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public UserDetailDomain setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Date getAccountExpiredTime() {
        return accountExpiredTime;
    }

    public void setAccountExpiredTime(Date accountExpiredTime) {
        this.accountExpiredTime = accountExpiredTime;
    }

    public Date getAccountLockedTime() {
        return accountLockedTime;
    }

    public void setAccountLockedTime(Date accountLockedTime) {
        this.accountLockedTime = accountLockedTime;
    }

    public Date getCredentialsExpiredTime() {
        return credentialsExpiredTime;
    }

    public void setCredentialsExpiredTime(Date credentialsExpiredTime) {
        this.credentialsExpiredTime = credentialsExpiredTime;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean getAble() {
        return able;
    }

    public void setAble(boolean able) {
        this.able = able;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAliPayUserId() {
        return aliPayUserId;
    }

    public void setAliPayUserId(String aliPayUserId) {
        this.aliPayUserId = aliPayUserId;
    }

    public String getWeCharUserId() {
        return weCharUserId;
    }

    public void setWeCharUserId(String weCharUserId) {
        this.weCharUserId = weCharUserId;
    }
}
