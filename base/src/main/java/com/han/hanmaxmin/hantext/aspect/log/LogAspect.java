package com.han.hanmaxmin.hantext.aspect.log;

import com.han.hanmaxmin.common.log.L;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * @CreateBy Administrator
 * @Date 2017/11/4  15:53
 * @Description Log的Aspect
 */
@Aspect
public class LogAspect {
    private static final String TAG = "LogAspect";

    private static final String POINTCUT_METHOD = "execution(@com.han.hanmaxmin.hantext.aspect.log.Log * * (..)) && @annotation(log)";

//    @Pointcut(POINTCUT_METHOD) public void methodAnnotateLog() {}

        @After(POINTCUT_METHOD)
        public void aspectLog(JoinPoint joinPoint,Log log){
            String value = log.value();
            if(value!=null){
                L.i(TAG,value);
            }
        }

    ;


}
