package com.han.hanmaxmin.hantext.aspect.intent;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * @CreateBy Administrator
 * @Date 2017/11/4  17:03
 * @Description
 */
@Aspect
public class IntentAspect {
    private static final String TAG="IntentAspetct";
    private static final String POINT_CUT="execution(@com.han.hanmaxmin.hantext.aspect.intent.Intent * * (..)) && @annotation(clazz)";

    @After(POINT_CUT)
    public void intentActivity(JoinPoint joinPoint,Intent clazz){
        Class value = clazz.value();
        if(value!=null){
            L.i(TAG,"Activity跳转不是默认Activity" );
            android.content.Intent intent=new android.content.Intent(HanHelper.getInstance().getScreenHelper().currentActivity(),value);
            HanHelper.getInstance().getApplication().startActivity(intent);
        }
        L.i(TAG,"Activity跳转是默认的Main2Activity");
    }

}
