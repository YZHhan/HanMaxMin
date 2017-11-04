package com.han.hanmaxmin.hantext.aspect.intent;

import com.han.hanmaxmin.activity.Main2Activity;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @CreateBy Administrator
 * @Date 2017/11/4  17:01
 * @Description
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Intent {

    Class value()default Main2Activity.class;
}
