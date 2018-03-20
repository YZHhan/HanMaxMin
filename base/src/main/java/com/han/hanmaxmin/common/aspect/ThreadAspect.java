package com.han.hanmaxmin.common.aspect;

import android.os.Looper;

import com.han.hanmaxmin.common.exception.HanException;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  11:49
 * @Description Proceedingjoinpoint 继承了 JoinPoint 。是在JoinPoint的基础上暴露出 proceed 这个方法。
 * proceed很重要，这个是aop代理链执行的方法。暴露出这个方法，
 * 就能支持 aop:around 这种切面（而其他的几种切面只需要用到JoinPoint，这跟切面类型有关），
 * 能决定是否走代理链还是走自己拦截的其他逻辑
 * 建议看一下 JdkDynamicAopProxy的invoke方法，了解一下代理链的执行原理。这样你就能明白 proceed方法的重要性。
 * <p>
 * ProceedingJoinPoint：继承JoinPoint。
 * toShortString：返回一个缩写连接点的字符串表示。
 * toLongString：返回一个长字符串表示的连接点。
 * getTarget:返回目标对象，切入点目标。
 */
@Aspect
public class ThreadAspect {

    private static final String TAG = "ThreadAspect";

    private static final String POINTCUT_METHOD_MAIN        = "execution(@com.han.hanmaxmin.common.aspect.thread.ThreadPoint(com.han.hanmaxmin.common.aspect.thread.ThreadType.MAIN) * *(..))";
    private static final String POINTCUT_METHOD_HTTP        = "execution(@com.han.hanmaxmin.common.aspect.thread.ThreadPoint(com.han.hanmaxmin.common.aspect.thread.ThreadType.HTTP) * *(..))";
    private static final String POINTCUT_METHOD_WORK        = "execution(@com.han.hanmaxmin.common.aspect.thread.ThreadPoint(com.han.hanmaxmin.common.aspect.thread.ThreadType.WORK) * *(..))";
    private static final String POINTCUT_METHOD_SINGLE_WORK = "execution(@com.han.hanmaxmin.common.aspect.thread.ThreadPoint(com.han.hanmaxmin.common.aspect.thread.ThreadType.SINGLE_WORK) * *(..))";

    @Pointcut(value = POINTCUT_METHOD_MAIN) public void onMainPoint() {

    }

    @Pointcut(value = POINTCUT_METHOD_HTTP) public void onHttpPoint() {

    }

    @Pointcut(value = POINTCUT_METHOD_WORK) public void onWorkPoint() {

    }

    @Pointcut(value = POINTCUT_METHOD_SINGLE_WORK) public void onSingleWorkPoint() {

    }

    /**
     * 主线程的切点，
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("onMainPoint()") public Object onMainExecutor(final ProceedingJoinPoint joinPoint) throws Throwable {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            L.i(TAG, joinPoint.toShortString() + "in the main thread... ");
            return joinPoint.proceed();
        } else {
            HanHelper.getInstance().getThreadHelper().getMainThread().execute(new Runnable() {
                @Override public void run() {
                    L.i(TAG, joinPoint.toShortString() + "in the main thread... ");
                    startOriginalMethod(joinPoint);
                }
            });
        }
        return null;
    }


    /**
     * 网络线程
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("onHttpPoint()") public Object onHttpExecutor(final ProceedingJoinPoint joinPoint) throws Throwable {
        HanHelper.getInstance().getThreadHelper().getHttpThreadPoll().execute(new Runnable() {
            @Override public void run() {
                L.i(TAG, joinPoint.toShortString() + "in http thread... ");
                startOriginalMethod(joinPoint);
            }
        });
        return null;
    }

    @Around("onWorkPoint()") public Object onWorkExecutor(final ProceedingJoinPoint joinPoint) throws Throwable {
        HanHelper.getInstance().getThreadHelper().getWorkThreadPoll().execute(new Runnable() {
            @Override public void run() {
                L.i(TAG, joinPoint.toShortString() + "in work thread... ");
                startOriginalMethod(joinPoint);
            }
        });
        return null;
    }

    @Around("onSingleWorkPoint()") public Object onSingleWorkExecutor(final ProceedingJoinPoint joinPoint) throws Throwable {
        HanHelper.getInstance().getThreadHelper().getSingleThreadPoll().execute(new Runnable() {
            @Override public void run() {
                L.i(TAG, joinPoint.toShortString() + "in single work thread... ");
                startOriginalMethod(joinPoint);
            }
        });
        return null;
    }

    /**
     * 执行原始方法，将异常映射到{@link com.han.hanmaxmin.mvp.presenter.HanPresenter#methodError(HanException)}
     *
     * @param joinPoint
     */
    private void startOriginalMethod(ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
        } catch (final HanException e0) {
            try {
                final Object target = joinPoint.getTarget();
                final Method methodError = target.getClass().getMethod("methodError", HanException.class);
                if (methodError != null) HanHelper.getInstance().getThreadHelper().getMainThread().execute(new Runnable() {
                    @Override public void run() {
                        try {
                            methodError.invoke(target, e0);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                });
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


}
