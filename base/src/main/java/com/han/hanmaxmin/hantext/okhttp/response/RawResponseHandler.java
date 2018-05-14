package com.han.hanmaxmin.hantext.okhttp.response;

import com.han.hanmaxmin.hantext.okhttp.HanOkHttp;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/12
 * @Description
 */

public abstract class RawResponseHandler implements IResponseHandler {
    @Override
    public void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String responseBodyStr = "";
        try {
            responseBodyStr = responseBody.string();
        } catch (IOException e){
            e.printStackTrace();
            HanOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "fail read response body");
                }
            });
            return;
        } finally {
            response.close();
        }

        final String finalResponseBodyStr = responseBodyStr;
        HanOkHttp.mHandler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(response.code(),finalResponseBodyStr);
            }
        });
    }

    public abstract void onSuccess(int statusCode, String response);

    @Override
    public void onFailure(int statueCode, String errorMessage) {

    }

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}
