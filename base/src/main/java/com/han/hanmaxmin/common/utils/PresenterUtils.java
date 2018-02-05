package com.han.hanmaxmin.common.utils;

import com.han.hanmaxmin.mvp.HanIView;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  14:12
 * @Description  业务类  P 层  工具类。
 */

public final class PresenterUtils {

//            public static <P extends HanPresenter, V extends HanIView> P createPresenter(V  iView){
//                Class<? extends HanIView> viewClass = iView.getClass();
//                P presenterImpl;
//                Type genericSuperclass = viewClass.getGenericSuperclass();//getGenericSuperclass 可以获取当前对象的直接超类的Type
//                if(genericSuperclass instanceof ParameterizedType){//ParameterizedType  是一个接口，用来检验泛型是否被参数化
//                    Type[] typeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();//返回表示此类型实际类型参数的type对象数组
//                    if(typeArguments != null && typeArguments.length > 0){
//                        Class typeArgument = (Class) typeArguments[0];
//                        try {
//                            presenterImpl = (P) typeArgument.newInstance();//  和new的作用一样，有区别
////                            presenterImpl.in
//
//                        } catch (InstantiationException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//
//            }

}
