package com.school.oauth2.endpoint.inter;

import java.lang.annotation.*;

/**
 * 此注解用于展示被标记的接口是属于user/guest/admin
 * 如果接口属于guest，则不用标记
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface Authentication {

    Class<?>[] group() default {};
}
