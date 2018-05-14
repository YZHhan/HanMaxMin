package com.han.hanmaxmin.hantext.okhttp.builder;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.hantext.okhttp.HanOkHttp;
import com.han.hanmaxmin.hantext.okhttp.callback.MyCallBack;
import com.han.hanmaxmin.hantext.okhttp.response.IResponseHandler;

import okhttp3.Request;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/12
 * @Description
 */

public  class DeleteBuilder extends OkHttpRequestBuilderParams<DeleteBuilder> {
    public DeleteBuilder(HanOkHttp hanOkHttp) {
        super(hanOkHttp);
    }

    @Override
    public void enqueue(IResponseHandler responseHandler) {
        try {
            if (mUrl == null || mUrl.length() == 0){
                throw new IllegalArgumentException("url can not null!");
            }
            Request.Builder builder = new Request.Builder().url(mUrl);
            appendHeaders(builder, mHeaders);

            if(mTag != null){
                builder.tag(mTag);
            }

            Request request = builder.build();

            mHanOkHttp.getOkHttpClient()
                        .newCall(request)
                        .enqueue(new MyCallBack(responseHandler));

        }catch (Exception e){
            L.e("Http Delete", "Delete enqueue error = " + e.getMessage());
            responseHandler.onFailure(0, e.getMessage());
        }

    }


}
