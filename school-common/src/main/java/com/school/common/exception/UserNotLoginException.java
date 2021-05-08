package com.school.common.exception;

public class UserNotLoginException extends CommonException {
    public UserNotLoginException() {
        super("当前线程没有登陆用户");
    }
}
