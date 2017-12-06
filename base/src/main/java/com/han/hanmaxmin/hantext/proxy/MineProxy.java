package com.han.hanmaxmin.hantext.proxy;

import com.han.hanmaxmin.common.log.L;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @CreateBy Administrator
 * @Date 2017/11/24  11:29
 * @Description
 */

public class MineProxy implements InvocationHandler {
    private Object object;

    public MineProxy(Object object) {
        this.object=object;
    }

    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        L.i("Proxy","我是在invoke 之前");

        L.i("Proxy","Method："+method);

        method.invoke(object,args);

        L.i("Proxy","我是在invoke 之hou");
        return null;
    }
}
