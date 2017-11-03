package com.han.hanmaxmin.common.aspect.network.parameter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  11:34
 * @Description
 */

@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Body {

    String mimeType() default "application/json; charest=UTF-8";
}
