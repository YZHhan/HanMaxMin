package com.han.hanmaxmin.hantext.okhttp.response;

import android.util.JsonToken;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.hantext.okhttp.HanOkHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/12
 * @Description
 */

public abstract class JsonResponseHandler implements IResponseHandler {

    @Override
    public void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String responseBodyStr = "";

        try {
            responseBodyStr = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();

            HanOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "fail read response body");
                }
            });
            return;
        } finally {
            responseBody.close();
        }

        final String finalResponseBodyStr = responseBodyStr;

        try {
            final Object result = new JSONTokener(finalResponseBodyStr).nextValue();
            if (result instanceof JSONObject) {
                HanOkHttp.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(response.code(), (JSONObject) result);
                    }
                });
            } else if (result instanceof JSONArray) {
                HanOkHttp.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(response.code(), (JSONArray) result);
                    }
                });
            } else {
                HanOkHttp.mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFailure(response.code(), "fail parse jsonobject, body=" + finalResponseBodyStr);
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
            HanOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "fail parse jsonobject, body=" + finalResponseBodyStr);
                }
            });
        }
    }

    public void onSuccess(int statusCode, JSONObject response) {
        L.w("Http", "onSuccess(int statusCode, JSONObject response) was not overriden, but callback was received");
    }

    public void onSuccess(int statusCode, JSONArray response) {
        L.w("Http", "onSuccess(int statusCode, JSONArray response) was not overriden, but callback was received");
    }

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}
