package com.han.hanmaxmin.common.aspect.thread;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  11:40
 * @Description  线程切换必备
 * 注解到方法在在中，参数是枚举类型，自定义枚举添加到自定义注解
 *
 *
 * @Retention  RUNTIME 运行时可见的，
 *              ClASS 编译时注解
 */
@Documented
@Target(METHOD)
@Retention(CLASS)
public @interface ThreadPoint {
    ThreadType value();
}
