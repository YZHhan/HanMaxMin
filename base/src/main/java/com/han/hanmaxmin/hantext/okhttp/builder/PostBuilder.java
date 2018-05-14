package com.han.hanmaxmin.hantext.okhttp.builder;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.hantext.okhttp.HanOkHttp;
import com.han.hanmaxmin.hantext.okhttp.callback.MyCallBack;
import com.han.hanmaxmin.hantext.okhttp.response.IResponseHandler;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/11
 * @Description Post
 */

public class PostBuilder extends OkHttpRequestBuilderParams<PostBuilder> {
    private String mJsonParams = "";

    public PostBuilder(HanOkHttp hanOkHttp) {
        super(hanOkHttp);
    }

    public PostBuilder jsonParams(String json){
        this.mJsonParams = json;
        return this;
    }

    @Override
    public void enqueue(IResponseHandler responseHandler) {
        try {
            if(mUrl == null || mUrl.length() == 0){
                    throw new IllegalArgumentException("url can not be null !");
            }
            Request.Builder builder = new Request.Builder().url(mUrl);
            appendHeaders(builder, mHeaders);

            if (mTag != null) {
                builder.tag(mTag);
            }

            if(mJsonParams.length() > 0){
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mJsonParams);
                builder.post(body);
            } else {
                FormBody.Builder encodingBuilder = new FormBody.Builder();
                appendParams(encodingBuilder, mParams);
                builder.post(encodingBuilder.build());
            }

            Request request= builder.build();
            mHanOkHttp.getOkHttpClient()
                        .newCall(request)
                        .enqueue(new MyCallBack(responseHandler));

        } catch (Exception e){
            L.e("Http", "Post enqueue error : " + e.getMessage());
            responseHandler.onFailure(0, e.getMessage());
        }
    }

    private void appendParams(FormBody.Builder builder, Map<String, String> params){
        if(params != null && !params.isEmpty()){
            for (String key : params.keySet()){
                builder.add(key, params.get(key));
            }
        }
    }
}
