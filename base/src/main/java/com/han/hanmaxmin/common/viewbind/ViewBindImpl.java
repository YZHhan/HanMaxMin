package com.han.hanmaxmin.common.viewbind;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.han.hanmaxmin.common.viewbind.annotation.Bind;
import com.han.hanmaxmin.common.viewbind.annotation.OnClick;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;

/**
 * @CreateBy Administrator
 * @Date 2017/12/24  22:08
 * @Description
 */

public final class ViewBindImpl implements ViewBind {

    private static final HashSet<Class<?>> IGNORED = new HashSet<>();

    static {
        IGNORED.add(Object.class);
        IGNORED.add(Activity.class);
        IGNORED.add(FragmentActivity.class);
        IGNORED.add(AppCompatActivity.class);
        IGNORED.add(Fragment.class);
        IGNORED.add(android.app.Fragment.class);
        /*自己的封装的 类也需要忽略*/


    }

    @Override public void bind(Object handler, View view) {
        injectObject(handler, handler.getClass(), new ViewFinder(view));
    }

    private static void injectObject(Object handler, Class<?> clazz, ViewFinder finder) {
        if (clazz == null && IGNORED.contains(clazz)) {
            return;
        }
        injectObject(handler, clazz.getSuperclass(), finder);
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                Class<?> fieldType = field.getType();
                if (
                    /*不注入静态字段*/      Modifier.isStatic(field.getModifiers()) ||
                    /*不注入final字段*/     Modifier.isFinal(field.getModifiers()) ||
                    /*不注入基本类型字段*/   fieldType.isPrimitive() ||
                    /*不注入数组类型的字段*/ fieldType.isArray()) {
                    continue;
                }

                Bind bind = field.getAnnotation(Bind.class);
                if (bind != null) {
                    try {
                        View view = finder.findViewById(bind.value(), bind.parentId());
                        if (view != null) {
                            field.setAccessible(true);
                            field.set(handler, view);
                        } else {
                            throw new RuntimeException("Invalid @Bind for " + clazz.getSimpleName() + "." + field.getName());
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Method[] methods = clazz.getDeclaredMethods();
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                if (
                    /*不注入静态方法*/ Modifier.isStatic(method.getModifiers())) {
                    continue;
                }
                OnClick annotation = method.getAnnotation(OnClick.class);
                if (annotation != null) {
                    int[] values = annotation.value();
                    int[] pIds = annotation.parentId();
                    int length = pIds.length;
                    for (int i = 0; i < length; i++){
                        int value = values[i];
                        if(value > 0){
                            ViewInfo info =new ViewInfo();
                            info.value = value;
                            info.parentId = length > i ? pIds[i] : 0;
                            method.setAccessible(true);
                            ////////
                            ///////
                            //////
                            ////
                            ///
                            //
                        }


                    }

                    String method1 = annotation.method();


                }

            }
        }


    }


}





