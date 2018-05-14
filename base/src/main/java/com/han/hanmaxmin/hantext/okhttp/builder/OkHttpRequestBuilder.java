package com.han.hanmaxmin.hantext.okhttp.builder;

import android.support.v4.os.IResultReceiver;

import com.han.hanmaxmin.hantext.okhttp.HanOkHttp;
import com.han.hanmaxmin.hantext.okhttp.response.IResponseHandler;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/11
 * @Description no param base request body  不带params的base request body
 * url tag header 。
 * 解析 ：
 * 作为Body的基础类，分为url和tag和header   以及Header.Builder
 *
 */

public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {
    protected String mUrl;
    protected Object mTag;
    protected Map<String, String> mHeaders;

    protected HanOkHttp mHanOkHttp;

    public OkHttpRequestBuilder(HanOkHttp hanOkHttp) {
        this.mHanOkHttp = hanOkHttp;
    }

     abstract void enqueue(final IResponseHandler responseHandler);

    /**
     * set url
     */
    public T url(String url) {
        this.mUrl = url;
        return (T) this;
    }

    /**
     * set tag
     */
    public T tag(String tag) {
        this.mTag = tag;
        return (T) this;
    }

    /**
     * set header<Map>
     */
    public T headers(Map<String, String> headers){
        this.mHeaders = headers;
        return (T) this;
    }

    /**
     *  add header
     */
    public T addHeader(String key, String value){
        if(this.mHeaders == null){
            mHeaders = new LinkedHashMap<>();
        }
        mHeaders.put(key, value);
        return (T) this;
    }

    /**
     * append headers into builder
     */
    protected void appendHeaders(Request.Builder builder, Map<String, String> headers){
        Headers.Builder headerBuilder = new Headers.Builder();
        if(headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()){
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }






}
