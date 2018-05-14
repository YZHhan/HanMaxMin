package com.han.hanmaxmin.hantext.okhttp.interceptor;

import android.os.Build;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/11
 * @Description  网路拦截器
 */

public class LoggingInterceptor implements Interceptor {
    public static final String USER_AGENT = "User-Agent";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()//
                            .newBuilder()//
                            .addHeader(USER_AGENT, createUserAgent())//
                            .build();

        return chain.proceed(request);
    }

    private String createUserAgent(){
        return  Build.BRAND + "/" + Build.MODEL + "/" + Build.VERSION.RELEASE;
    }
}
