package com.han.hanmaxmin.hantext.aspect;

import org.aspectj.lang.annotation.Aspect;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  16:33
 * @Description
 */
@Aspect
public class ToastAspect {
    public static final String POINTCUT_METHOD_DEFAULT="execution(@com.han.hanmaxmin.hantext.aspect.Toast ** (..) )";


}
