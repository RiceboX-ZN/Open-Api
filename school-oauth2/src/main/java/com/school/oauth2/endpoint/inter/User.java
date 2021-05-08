package com.school.oauth2.endpoint.inter;

import java.lang.annotation.*;

/**
 * 此注解只用作用于方法上面，只起标记作用
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface User {

}
