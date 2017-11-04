package com.han.hanmaxmin.common.aspect;

import android.support.v4.app.FragmentActivity;

import com.han.hanmaxmin.common.aspect.permission.Permission;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.utils.permission.PermissionBuilder;
import com.han.hanmaxmin.common.utils.permission.PermissionCallbackListener;
import com.han.hanmaxmin.common.utils.permission.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  11:48
 * @Description
 *
 */
@Aspect
public class PermissionAspect {
public static final String TAG="PermissionAspect";
    private static final String POINTCUT_METHOD_DEFAULT="execution(@com.han.hanmaxmin.common.aspect.permission.Permission * *(..)) && @annotation(permission)";
    @Around(POINTCUT_METHOD_DEFAULT)
    public Object onPermissionExecution(final ProceedingJoinPoint joinPoint, final Permission permission){
        startRequestPermission(joinPoint, permission);

        return null;
    }

    /**
     * 申请权限
     */
    private void startRequestPermission(final ProceedingJoinPoint joinPoint, final Permission permission) {
        if (permission == null) return;
        L.i(TAG,"permission"+permission.value()[0]);
        String[] values = permission.value();
        if (values.length < 1) {
            return;
        }

        FragmentActivity activity = HanHelper.getInstance().getScreenHelper().currentActivity();
        if (activity != null) {
            PermissionBuilder builder = PermissionUtils.getInstance().createBuilder();
            for (String permissionStr : values) {
                builder.addWantPermission(permissionStr);
            }
            builder.setActivity(activity)//
                    .setShowCustomDialog(true)//
                    .setListener(new PermissionCallbackListener() {
                        @Override public void onPermissionCallback(int requestCode, boolean isGrantedAll) {
                            if (permission.forceGoOn() || isGrantedAll) {
                                try {
                                    joinPoint.proceed();
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }else{
                                L.e("PermissionAspect","current permission is not allow, you can set @Permission(forceGoOn = true), it will run the method whether permission is granted!!");
                            }
                        }
                    });

            builder.start();
        }
    }
}
