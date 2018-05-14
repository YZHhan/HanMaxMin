package com.han.hanmaxmin.hantext.okhttp.builder;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.hantext.okhttp.HanOkHttp;
import com.han.hanmaxmin.hantext.okhttp.callback.MyCallBack;
import com.han.hanmaxmin.hantext.okhttp.response.IResponseHandler;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/11
 * @Description
 */

public class PatchBuilder extends OkHttpRequestBuilderParams <PatchBuilder>{

    public PatchBuilder(HanOkHttp hanOkHttp) {
        super(hanOkHttp);
    }

    @Override
    public void enqueue(IResponseHandler responseHandler) {
        try {
            if(mUrl == null || mUrl.length() == 0){
                throw new IllegalArgumentException("url can not be null");
            }

            Request.Builder builder = new Request.Builder().url(mUrl);
            appendHeaders(builder, mHeaders);

            if(mTag != null){
                builder.tag(mTag);
            }

            builder.patch(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), ""));
            Request request = builder.build();
            mHanOkHttp.getOkHttpClient()
                        .newCall(request)
                        .enqueue(new MyCallBack(responseHandler));

        } catch (Exception e){
            L.e("Http","Patch enqueue error = " +e.getMessage());
            responseHandler.onFailure(0, e.getMessage());

        }
    }
}
