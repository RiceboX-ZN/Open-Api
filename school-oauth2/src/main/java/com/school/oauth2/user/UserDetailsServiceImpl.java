package com.school.oauth2.user;

import com.school.common.exception.CommonException;
import com.school.oauth2.endpoint.domain.Captcha;
import com.school.oauth2.endpoint.domain.UserInfo;
import com.school.oauth2.endpoint.mapper.CaptchaMapper;
import com.school.oauth2.endpoint.mapper.UserMapper;
import com.school.oauth2.user.domain.UserDetailDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

public class UserDetailsServiceImpl implements UserDetailsService , Serializable {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CaptchaMapper captchaMapper;

    /**
     * 根据用户名查询出用户信息，然后封装到Security的User中，框架会自动用户名和密码是否正确
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //检验验证码
        if (!validCaptcha()) {
            throw new CommonException("验证码-验证失败");
        }
        UserInfo userInfo = this.userMapper.selectByUsername(username);

        if (userInfo == null) {
            throw new CommonException("用户名输入错误");
        }
        //把查询出来的对象封装到Security的User中
        UserDetailDomain domain = new UserDetailDomain(userInfo.getUsername(), userInfo.getEncryptedPassword(), getGrantedAuthority(userInfo));
        domain.setUserId(userInfo.getUserId());
        domain.setNickname(userInfo.getNickname());
        domain.setPhone(userInfo.getPhone());
        domain.setEmail(userInfo.getEmail());
        domain.setIdd(userInfo.getIdd());
        domain.setGender(userInfo.getGender());
        domain.setAvatar(userInfo.getAvatar());
        domain.setSchoolName(userInfo.getSchoolName());
        domain.setAccountExpiredTime(userInfo.getAccountExpiredTime());
        domain.setAccountLockedTime(userInfo.getAccountLockedTime());
        domain.setCredentialsExpiredTime(userInfo.getCredentialsExpiredTime());
        domain.setAdmin(userInfo.getAdmin());
        domain.setAble(userInfo.getAble());
        domain.setUserType(userInfo.getUserType());
        domain.setAliPayUserId(userInfo.getAliPayUserId());
        domain.setWeCharUserId(userInfo.getWeChatUserId());
        return domain;
    }

    public Collection<GrantedAuthority> getGrantedAuthority(UserInfo userInfo) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (userInfo.getRoleList() != null) {
            userInfo.getRoleList().forEach( role -> {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            });
        }
        return grantedAuthorities;
    }
    /**
     * 校验验证码
     * @return
     */
    public boolean validCaptcha() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            Captcha captcha = new Captcha();

            String uuid = request.getParameter("captchaUuid");
            String captchaCode = request.getParameter("captchaCode");
            if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(captchaCode)) {
                throw new CommonException("验证码或者captchaUuid为空：" + captchaCode);
            }

            captcha.setUuid(uuid);
            captcha.setCode(captchaCode);
            Captcha result = this.captchaMapper.selectOne(captcha);
            if (result == null || result.getCode() == null) {
                throw new CommonException("验证码错误" + captchaCode);
            }
            this.captchaMapper.delete(result);
            if (result.getExpireTime().after(new Date())) {
                throw new CommonException("验证码失效，请重新获取，过期时间：" + result.getExpireTime());
            }
            return true;
        }
        return false;
    }
}
