package com.han.hanmaxmin.hantext.proxy.doingproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by ptxy on 2018/2/6.
 */

public class ProxyHandler implements InvocationHandler {
    private RealAdapter realAdapter;
    public ProxyHandler(RealAdapter realAdapter) {
        this.realAdapter = realAdapter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
