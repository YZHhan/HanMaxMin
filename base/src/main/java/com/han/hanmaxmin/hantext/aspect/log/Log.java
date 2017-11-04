package com.han.hanmaxmin.hantext.aspect.log;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @CreateBy Administrator
 * @Date 2017/11/4  15:51
 * @Description  自定义注解的Log
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Log {
    String value();
}
