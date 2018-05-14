package com.han.hanmaxmin.hantext.okhttp.builder;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.hantext.okhttp.HanOkHttp;
import com.han.hanmaxmin.hantext.okhttp.callback.MyCallBack;
import com.han.hanmaxmin.hantext.okhttp.response.IResponseHandler;

import java.util.Map;

import okhttp3.Request;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/11
 * @Description
 */

public class GetBuilder extends OkHttpRequestBuilderParams<GetBuilder> {

    public GetBuilder(HanOkHttp hanOkHttp) {
        super(hanOkHttp);
    }

    @Override
    public void enqueue(IResponseHandler responseHandler) {
        try {
            if (mUrl == null || mUrl.length() == 0) {
                throw new IllegalArgumentException("url can not be null");
            }

            if (mParams != null || mParams.size() > 0) {
                mUrl = appendParams(mUrl, mParams);
            }

            Request.Builder builder = new Request.Builder().url(mUrl).get();
            appendHeaders(builder, mHeaders);

            if (mTag != null) {
                builder.tag(mTag);
            }

            Request request = builder.build();

            mHanOkHttp.getOkHttpClient()
                    .newCall(request)
                    .enqueue(new MyCallBack(responseHandler));

        } catch (Exception e) {
            L.e("Http","Get enqueue error" + e.getMessage());
            responseHandler.onFailure(0,e.getMessage());
        }
    }

    private String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }


}
