package com.han.hanmaxmin.common.viewbind;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.han.hanmaxmin.common.viewbind.annotation.Bind;
import com.han.hanmaxmin.common.viewbind.annotation.OnClick;
import com.han.hanmaxmin.mvp.HanABActivity;
import com.han.hanmaxmin.mvp.HanActivity;
import com.han.hanmaxmin.mvp.HanViewPagerABActivity;
import com.han.hanmaxmin.mvp.HanViewPagerActivity;
import com.han.hanmaxmin.mvp.adapter.HanListAdapterItem;
import com.han.hanmaxmin.mvp.adapter.HanRecyclerAdapterItem;
import com.han.hanmaxmin.mvp.adapter.HanTabViewPagerAdapter;
import com.han.hanmaxmin.mvp.adapter.HanViewPagerAdapter;
import com.han.hanmaxmin.mvp.fragment.HanFragment;
import com.han.hanmaxmin.mvp.fragment.HanHeaderViewPagerFragment;
import com.han.hanmaxmin.mvp.fragment.HanIHeaderViewPagerFragment;
import com.han.hanmaxmin.mvp.fragment.HanListFragment;
import com.han.hanmaxmin.mvp.fragment.HanPullHeaderViewPagerFragment;
import com.han.hanmaxmin.mvp.fragment.HanPullListFragment;
import com.han.hanmaxmin.mvp.fragment.HanPullRecyclerFragment;
import com.han.hanmaxmin.mvp.fragment.HanRecyclerFragment;
import com.han.hanmaxmin.mvp.fragment.HanViewPagerFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * @CreateBy Administrator
 * @Date 2017/12/24  22:08
 * @Description
 */

public final class ViewBindImpl implements ViewBind {

    private static final ArrayList<Class<?>> IGNORED = new ArrayList<>();

    static {
        IGNORED.add(Object.class);
        IGNORED.add(Activity.class);
        IGNORED.add(FragmentActivity.class);
        IGNORED.add(AppCompatActivity.class);
        IGNORED.add(Fragment.class);
        IGNORED.add(android.app.Fragment.class);

        IGNORED.add(HanActivity.class);
        IGNORED.add(HanABActivity.class);
        IGNORED.add(HanViewPagerActivity.class);
        IGNORED.add(HanViewPagerABActivity.class);

        IGNORED.add(HanFragment.class);
        IGNORED.add(HanListFragment.class);
        IGNORED.add(HanPullListFragment.class);
        IGNORED.add(HanRecyclerFragment.class);
        IGNORED.add(HanPullRecyclerFragment.class);
        IGNORED.add(HanViewPagerFragment.class);
        IGNORED.add(HanHeaderViewPagerFragment.class);
        IGNORED.add(HanPullHeaderViewPagerFragment.class);

        IGNORED.add(HanViewPagerAdapter.class);
        IGNORED.add(HanTabViewPagerAdapter.class);
        IGNORED.add(HanListAdapterItem.class);
        IGNORED.add(HanRecyclerAdapterItem.class);
        /*自己的封装的 类也需要忽略*/
    }

    @Override public void bind(Object handler, View view) {
        injectObject(handler, handler.getClass(), new ViewFinder(view));
    }

    private static void injectObject(Object handler, Class<?> clazz, ViewFinder finder) {
        if (clazz == null || IGNORED.contains(clazz)) {
            return;
        }
        injectObject(handler, clazz.getSuperclass(), finder);
        Field[] fields = clazz.getDeclaredFields();//获取自己声明的各种字段，包括public，protected，private
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                Class<?> fieldType = field.getType();// 获取属性声明时类型对象，返回（Class对象）
                if (
                    /*不注入静态字段*/      Modifier.isStatic(field.getModifiers()) ||// 获取属性的修饰getModifiers
                    /*不注入final字段*/     Modifier.isFinal(field.getModifiers()) ||
                    /*不注入基本类型字段*/   fieldType.isPrimitive() ||// 判断是否是原始类型。boolean、char、byte、short、int、long、float、double
                    /*不注入数组类型的字段*/ fieldType.isArray()) {
                    continue;
                }

                Bind bind = field.getAnnotation(Bind.class);
                if (bind != null) {
                    try {
                        View view = finder.findViewById(bind.value(), bind.parentId());// 通过注解的value和parentId
                        if (view != null) {
                            field.setAccessible(true);// 将一个类的成员变量设置为private
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

        Method[] methods = clazz.getDeclaredMethods();// 返回 Method 对象的一个数组，这些对象反映此 Class 对象表示的类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                if (
                    /*不注入静态方法*/ Modifier.isStatic(method.getModifiers())) {
                    continue;
                }
                OnClick annotation = method.getAnnotation(OnClick.class);
                if (annotation != null) {
                    try {
                        int[] values = annotation.value();
                        int[] pIds = annotation.parentId();
                        int parentIdsLen = pIds.length;
                        for (int i = 0; i < values.length; i++){
                            int value = values[i];
                            if(value > 0){
                                ViewInfo info =new ViewInfo();
                                info.value = value;
                                info.parentId = parentIdsLen > i ? pIds[i] : 0;
                                method.setAccessible(true);
                                EventListenerManager.addEventMethod(finder, info, annotation, handler, method);
                            }
                        }
                    } catch (Throwable ex){
                        ex.printStackTrace();
                    }
                }

            }
        }
    }
}





