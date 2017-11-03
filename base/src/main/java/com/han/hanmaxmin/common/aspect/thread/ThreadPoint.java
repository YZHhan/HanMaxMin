package com.han.hanmaxmin.common.aspect.thread;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  11:40
 * @Description
 */
@Documented
@Target(METHOD)
@Retention(CLASS)
public @interface ThreadPoint {
    ThreadType value();
}
