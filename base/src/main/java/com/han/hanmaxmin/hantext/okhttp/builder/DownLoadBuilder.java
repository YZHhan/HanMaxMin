package com.han.hanmaxmin.hantext.okhttp.builder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telecom.Call;

import com.han.hanmaxmin.hantext.okhttp.HanOkHttp;
import com.han.hanmaxmin.hantext.okhttp.body.ResponseProgressBody;
import com.han.hanmaxmin.hantext.okhttp.callback.MyDownloadCallback;
import com.han.hanmaxmin.hantext.okhttp.response.DownloadResponseHandler;
import com.han.hanmaxmin.hantext.okhttp.response.IResponseHandler;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/12
 * @Description
 */

public class DownLoadBuilder {

    private HanOkHttp mHanOkHttp;

    private String mUrl = "";
    private Object mTag;
    private Map<String, String> mHeaders;

    private String mFileDir = "";        //文件dir
    private String mFileName = "";       //文件名
    private String mFilePath = "";       //文件路径 （如果设置该字段则上面2个就不需要）

    private Long mCompleteBytes = 0L;    //已经完成的字节数 用于断点续传

    public DownLoadBuilder(HanOkHttp myOkHttp) {
        mHanOkHttp = myOkHttp;
    }

    public DownLoadBuilder url(@NonNull String url) {
        this.mUrl = url;
        return this;
    }

    /**
     * set file storage dir
     */
    public DownLoadBuilder fileDir(@NonNull String fileDir) {
        this.mFileDir = fileDir;
        return this;
    }

    /**
     * set file storage name
     */
    public DownLoadBuilder fileName(@NonNull String fileName) {
        this.mFileName = fileName;
        return this;
    }

    /**
     * set file path
     */
    public DownLoadBuilder filePath(@NonNull String filePath) {
        this.mFilePath = filePath;
        return this;
    }

    /**
     * set tag
     */
    public DownLoadBuilder tag(@NonNull Object tag) {
        this.mTag = tag;
        return this;
    }

    /**
     * set headers
     */
    public DownLoadBuilder headers(@NonNull Map<String, String> headers) {
        this.mHeaders = headers;
        return this;
    }

    /**
     * set one header
     */
    public DownLoadBuilder addHeader(@NonNull String key, @NonNull String val) {
        if (this.mHeaders == null) {
            mHeaders = new LinkedHashMap<>();
        }
        mHeaders.put(key, val);
        return this;
    }

    /**
     * set completed bytes (BreakPoints)
     *
     * @param completeBytes 已经完成的字节数
     */
    public DownLoadBuilder setCompleteBytes(@NonNull long completeBytes) {
        if (completeBytes >= 0L) {
            this.mCompleteBytes = completeBytes;
            addHeader("RANGE", "bytes=" + completeBytes + "-");           //添加断点续传header
        }
        return this;
    }


    public okhttp3.Call enqueue(final DownloadResponseHandler downloadResponseHandler) {
        try {
            if (mUrl == null || mUrl.length() == 0) {
                throw new IllegalArgumentException("url can not be null");
            }

            if (mFilePath.length() == 0) {
                if (mFileDir.length() == 0 || mFileName.length() == 0) {
                    throw new IllegalArgumentException("FilePath can not be null !");
                } else {
                    mFilePath = mFileDir + mFileName;
                }
            }
            checkFilePath(mFilePath, mCompleteBytes);

            Request.Builder builder = new Request.Builder().url(mUrl);
            appendHeaders(builder, mHeaders);

            if (mTag != null) {
                builder.tag(mTag);
            }

            Request request = builder.build();

            okhttp3.Call call = mHanOkHttp.getOkHttpClient()
                    .newBuilder()
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Response originalResponse = chain.proceed(chain.request());
                            return originalResponse.newBuilder()
                                    .body(new ResponseProgressBody(originalResponse.body(), downloadResponseHandler))
                                    .build();
                        }
                    })
                    .build()
                    .newCall(request);

            call.enqueue(new MyDownloadCallback(downloadResponseHandler, mFilePath, mCompleteBytes));
            return call;
        } catch (Exception e) {
            downloadResponseHandler.onFailure(e.getMessage());
            return null;
        }
    }


    private void checkFilePath(String filePath, long completeBytes) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            return;
        }

        if (completeBytes > 0L) {       //如果设置了断点续传 则必须文件存在
            throw new Exception("断点续传文件" + filePath + "不存在！");
        }

        if (filePath.endsWith(File.separator)) {
            throw new Exception("创建文件" + filePath + "失败，目标文件不能为目录！");
        }

        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new Exception("创建目标文件所在目录失败！");
            }
        }

    }

    //append headers into builder
    private void appendHeaders(Request.Builder builder, Map<String, String> headers) {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }


}
