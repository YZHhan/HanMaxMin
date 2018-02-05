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

class ProxyHandler implements InvocationHandler{
    private Object tar;

    public  Object bind(Object tar){
        this.tar = tar;
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(), tar.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return null;
    }
}
