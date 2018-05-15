package com.han.hanmaxmin.hantext.okhttp.body;

import android.support.annotation.Nullable;

import com.han.hanmaxmin.hantext.okhttp.response.DownloadResponseHandler;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/12
 * @Description
 */

public class ResponseProgressBody extends ResponseBody {

    private ResponseBody mResponseBody;
    private DownloadResponseHandler mDownloadResponseHandler;
    private BufferedSource bufferedSource;

    public ResponseProgressBody(ResponseBody responseBody, DownloadResponseHandler downloadResponseHandler) {
        this.mResponseBody = responseBody;
        this.mDownloadResponseHandler = downloadResponseHandler;
    }


    @Nullable
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            Okio.buffer(source(mResponseBody.source()));

        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source()) {

            long totalBytesRead;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                //这个的进度应该是读取response每次内容的进度，在写文件进度之前 所以暂时以写完文件的进度为准
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += ((bytesRead != -1) ? bytesRead : 0);
                return bytesRead;
            }
        };
    }
}