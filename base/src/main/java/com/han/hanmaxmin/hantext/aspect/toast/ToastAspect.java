package com.han.hanmaxmin.hantext.aspect.toast;

import com.han.hanmaxmin.common.log.L;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  16:33
 * @Description
 */
@Aspect
public class ToastAspect {
    private static final String TAG                     = "ToastAspect";
    public static final  String POINTCUT_METHOD_DEFAULT = "execution(@com.han.hanmaxmin.hantext.aspect.toast.Toast * * (..)) && @annotation(toast)";

    @Before(POINTCUT_METHOD_DEFAULT) public void toastAfter(JoinPoint joinPoint,Toast toast) {
        String simpleName = joinPoint.getClass().getSimpleName();
        L.i(TAG, simpleName);
        String value = toast.value();
        if(value!=null){
            T.show(value);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        L.i(TAG, "当前时间：" + df.format(new Date()));
        L.i(TAG, "AOP_________________________________qie面");
    }

}
















