package com.han.hanmaxmin.hantext.okhttp.builder;

import com.han.hanmaxmin.hantext.aspect.toast.T;
import com.han.hanmaxmin.hantext.okhttp.HanOkHttp;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/11
 * @Description 带有param的base request body
 */

public abstract class OkHttpRequestBuilderParams <T extends OkHttpRequestBuilderParams> extends OkHttpRequestBuilder <T> {
   protected Map<String, String> mParams;

    public OkHttpRequestBuilderParams(HanOkHttp hanOkHttp) {
        super(hanOkHttp);
    }

    /**
     * set map params
     */
    public T params(Map<String, String> params){
        this.mParams = params;
        return (T) this;
    }

    /**
     * add params
     */
    public T addParam(String key, String value) {
        if (this.mParams == null) {
            mParams = new LinkedHashMap<>();
        }
        mParams.put(key, value);
        return (T) this;
    }

}
