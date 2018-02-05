package com.han.hanmaxmin.common.aspect.permission;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @CreateBy Administrator
 * @Date 2017/11/3  11:38
 * @Description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {
    String [] value()   default {};
    
    boolean forceGoOn() default false;
}
