package com.han.hanmaxmin.hantext.okhttp.response;

import okhttp3.Response;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/11
 * @Description
 */

public interface IResponseHandler {
    void onSuccess(Response response);

    void onFailure(int statueCode, String errorMessage);

    void onProgress(long currentBytes, long totalBytes);
}
