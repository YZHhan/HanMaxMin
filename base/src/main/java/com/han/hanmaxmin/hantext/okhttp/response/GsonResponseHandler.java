package com.han.hanmaxmin.hantext.okhttp.response;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.han.hanmaxmin.hantext.okhttp.HanOkHttp;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/12
 * @Description
 */

public abstract class GsonResponseHandler <T> implements IResponseHandler{
    private Type mType;

    public GsonResponseHandler() {
        Type genericSuperclass = getClass().getGenericSuperclass();//反射获取带泛型的class
        if(genericSuperclass instanceof Class){
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) genericSuperclass;//获取所有type
       mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]); //将泛型转为type
    }

    private Type getType() {
        return mType;
    }

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
            Gson gson = new Gson();
            final T gsonResponse = (T) gson.fromJson(finalResponseBodyStr, getType());

            HanOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(response.code(), gsonResponse);
                }
            });
        } catch (Exception e){
            e.printStackTrace();

            HanOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "fail parse gson, body=" + finalResponseBodyStr);
                }
            });
        }
    }

    public abstract void onSuccess(int statusCode, T response);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}

