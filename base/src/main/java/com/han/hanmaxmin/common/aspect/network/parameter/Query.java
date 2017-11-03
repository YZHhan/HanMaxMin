package com.han.hanmaxmin.common.aspect.network.parameter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  11:31
 * @Description
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Query {
    /**
     * 查询参数的名称
     */
    String value();
}
