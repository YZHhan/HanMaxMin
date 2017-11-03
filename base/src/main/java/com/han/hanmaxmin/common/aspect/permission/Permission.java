package com.han.hanmaxmin.common.aspect.permission;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  11:38
 * @Description
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Permission {

    String [] value()   default {};
    boolean forceGoOn() default false;
}
