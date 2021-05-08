package com.school.zuul.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({AuthenticationConfig.class})
public @interface EnableGateWay {
}
