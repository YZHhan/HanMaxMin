package com.han.hanmaxmin.common.viewbind.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description  事件注解
 * 被注解的方法必须具备以下形式
 * 1。private 修饰
 * 2.返回值类型没有要求
 * 3.参数签名和type的接口要求的参数签名一致.
 *
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {

    /**
     *控件ID的集合，id小于1时不执行UI事件的绑定
     */
    int [] value();

    /**
     * 控件的parent 控件id集合，组合为（value[i] ,parnetId[i] or 0 ）
     */
    int [] parentId () default 0;

    /**
     * 事件的linstener ,默认的点击事件
     */
    Class <?> type () default View.OnClickListener.class;

    /**
     *事件的setter方法名，默默为set+type # simpleName
     */
    String  setter () default "";

    /**
     *如果type的接口类型提供多个方法时，需要此参数指定发方法名
     */
    String method () default "";
}
