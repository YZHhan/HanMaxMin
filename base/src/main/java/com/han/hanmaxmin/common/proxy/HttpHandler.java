package com.han.hanmaxmin.common.proxy;

import com.han.hanmaxmin.common.http.HttpAdapter;
import com.han.hanmaxmin.common.log.L;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @CreateBy Administrator
 * @Date 2017/11/24  11:09
 * @Description 动态代理——网络层
 */

public class HttpHandler implements InvocationHandler{
    private static final String TAG="HttpHandler";

    private final HttpAdapter adapter;
    private final Object tag;

    public HttpHandler(HttpAdapter adapter, Object tag) {
        this.adapter = adapter;
        this.tag = tag;
    }




    @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        L.i(TAG,"网络请求代理接口："+method.getName()+" requestType:"+tag);
        return adapter.toString();
    }
}
