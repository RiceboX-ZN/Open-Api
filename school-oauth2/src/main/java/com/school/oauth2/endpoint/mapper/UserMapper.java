package com.school.oauth2.endpoint.mapper;

import com.school.oauth2.endpoint.domain.UserInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<UserInfo> {

    UserInfo selectByUsername(@Param("username") String username);

    UserInfo queryUserDetailsByUserId(@Param("userId") Long userId);

}
