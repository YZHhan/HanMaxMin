package com.han.hanmaxmin.common.viewbind.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @CreateBy Administrator
 * @Date 2017/12/24  19:31
 * @Description  ButterKnife   封装注解
 * 因为Bind 是整个 类中，所以采用的是域声明注解
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bind {
    int  value();
    int parentId() default 0;
}
