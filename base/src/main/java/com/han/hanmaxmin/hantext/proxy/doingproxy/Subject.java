package com.han.hanmaxmin.hantext.proxy.doingproxy;


import com.han.hanmaxmin.common.log.L;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 抽象对象
 */
public interface Subject {

    void doingProxy();

}

class RealSubject implements Subject{

    @Override
    public void doingProxy() {
        L.i("proxy", "我是真是对象的doingProxy");
    }
}

