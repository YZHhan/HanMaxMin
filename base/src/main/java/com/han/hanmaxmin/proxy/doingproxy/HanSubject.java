package com.han.hanmaxmin.proxy.doingproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 抽象对象
 */
public interface HanSubject {

    public void doingProxy();

}

/**
 * 真是对象
 */
class HanRealSubject implements HanSubject {


    @Override public void doingProxy() {
        System.out.print("doing  proxy....");
    }

}

/**
 * 代理对象
 *
 * Proxy  的静态方法  newProxyInstance（ClassLoader loader,Class<?>[] interfaces,InvocationHandler h）
 * ClassLoader ：类的加载器 。使用null，表示默认加载器
 * interfaces：需要代理的接口数组。
 * InvocationHandler ： 调用处理器，使用新建的Proxy对象调用方法的时候，都会调用到该接口的invoke的接口 。
 *
 *
 *
 */
class HanProxyHandler implements InvocationHandler {

    private Object tar;

    /**
     * 绑定委托对象，并返回代理类。
     */
    public Object bind(Object tar) {
        this.tar = tar;
        //绑定该类实现的所有接口，取得代理类。
        return Proxy.newProxyInstance(tar.getClass().getClassLoader(), //
                tar.getClass().getInterfaces(),//
                this//
        );
    }


    /**
     * @param proxy  可以使用反射获取代理代理对象的信息（也就是proxy.getClass().getName）
     *               可以将代理对象返回以进行连续调用，这就是proxy的存在的目的。因为this  并不是代理对象。
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;//被代理的类型为Object 基类。
        //这里就可以进行所谓的AOP 编程
        //在调用具体函数方法前，执行功能处理
        result = method.invoke(tar, args);
        return result;
    }

}

