package com.school.common.exception;

/**
 * 封装异常
 */
public class CommonException extends RuntimeException {

    public Object object;

    public CommonException() {
        super();
    }

    public CommonException(Object o) {
        super();
        this.object = o;
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    protected CommonException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }
}
