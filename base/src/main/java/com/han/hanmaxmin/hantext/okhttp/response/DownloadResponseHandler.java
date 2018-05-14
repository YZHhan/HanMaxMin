package com.han.hanmaxmin.hantext.okhttp.response;

import java.io.File;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/12
 * @Description
 */

public abstract class DownloadResponseHandler {
    public void onStart(long totalBytes){

    }

    public void onCancel(){

    }

    public abstract void onFinish(File downLoadFile);

    public abstract void onProgress(long currentBytes, long totalBytes);

    public abstract void onFailure(String error_msg);
}
