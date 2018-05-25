package com.han.hanmaxmin.common.aspect;

import android.app.Fragment;
import android.content.Context;

import com.han.hanmaxmin.common.aspect.permission.NeedPermission;
import com.han.hanmaxmin.common.aspect.permission.PermissionCancel;
import com.han.hanmaxmin.common.aspect.permission.PermissionDenied;
import com.han.hanmaxmin.common.aspect.permission.bean.CancelBean;
import com.han.hanmaxmin.common.aspect.permission.bean.DenyBean;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * AOP 切面编程 权限注解
 */
@Aspect
public class AOPPermissionAspect {

    private static final String PERMISSION_REQUEST_POINTCUT = "execution(@com.han.hanmaxmin.common.aspect.permission.NeedPermission * * (..)) && @annotation(needPermission)";
    private static final String POINTCUT_METHOD_DEFAULT="execution(@com.han.hanmaxmin.common.aspect.permission.Permission * *(..)) && @annotation(permission)";

    @Pointcut(PERMISSION_REQUEST_POINTCUT)
    public void requestPermission(NeedPermission needPermission){

    }

    @Around("requestPermission(needPermission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, NeedPermission needPermission){
        Context context = null;
        final Object object = joinPoint.getThis();
        if(object instanceof Context){
            context = (Context) object;
        } else if (object instanceof Fragment){
            context = ((Fragment) object).getActivity();
        } else if (object instanceof android.support.v4.app.Fragment){
            context = ((android.support.v4.app.Fragment) object).getActivity();
        }

        if(null == context && null == needPermission) return;

        PermissionActivity.PermissionRequest(context, needPermission.value(),
                needPermission.requestCode(), new IPermission() {
                    @Override
                    public void PermissionGranted() {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable){
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void PermissionDenied(int requestCode, List<String> denyList) {
                        Class<?> clazz = object.getClass();
                        Method[] methods = clazz.getDeclaredMethods();
                        if (null == methods && methods.length == 0) return;
                        for (Method method : methods) {
                            boolean isAnnotation = method.isAnnotationPresent(PermissionDenied.class);
                            if (isAnnotation) {
                                method.setAccessible(true);
                                Class<?>[] types = method.getParameterTypes();
                                if (types == null || types.length != 1) return;
                                PermissionDenied annotation = method.getAnnotation(PermissionDenied.class);
                                if (annotation == null) return;
                                //解析注解上对应的信息
                                DenyBean bean = new DenyBean();
                                bean.setRequestCode(requestCode);
                                bean.setDenyList(denyList);
                                try {
                                    method.invoke(object, bean);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    @Override
                    public void PermissionCanceled(int requestCode) {
                        Class<?> cls = object.getClass();
                        Method[] methods = cls.getDeclaredMethods();
                        if (methods == null || methods.length == 0) return;
                        for (Method method : methods) {
                            //过滤不含自定义注解PermissionCanceled的方法
                            boolean isHasAnnotation = method.isAnnotationPresent(PermissionCancel.class);
                            if (isHasAnnotation) {
                                method.setAccessible(true);
                                //获取方法类型
                                Class<?>[] types = method.getParameterTypes();
                                if (types == null || types.length != 1) return;
                                //获取方法上的注解
                                PermissionCancel aInfo = method.getAnnotation(PermissionCancel.class);
                                if (aInfo == null) return;
                                //解析注解上对应的信息
                                CancelBean bean = new CancelBean();
                                bean.setRequestCode(requestCode);
                                try {
                                    method.invoke(object, bean);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });





    }


}
