package com.han.hanmaxmin.hantext.okhttp.callback;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.hantext.okhttp.HanOkHttp;
import com.han.hanmaxmin.hantext.okhttp.response.IResponseHandler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/11
 * @Description
 */

public class MyCallBack implements Callback {
    private IResponseHandler mResponseHandler;

    public MyCallBack(IResponseHandler responseHandler) {
        this.mResponseHandler = responseHandler;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        L.e("HttpCallBack",e.toString());
        HanOkHttp.mHandler.post(new Runnable() {
            @Override
            public void run() {
                mResponseHandler.onFailure(0, e.toString());
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
            if(response.isSuccessful()){
                mResponseHandler.onSuccess(response);
            } else {
                L.e("Http Success","onResponse fail status = " +response.code());

                HanOkHttp.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mResponseHandler.onFailure(response.code(), "fail status = "+ response.code());
                    }
                });
            }
    }
}
