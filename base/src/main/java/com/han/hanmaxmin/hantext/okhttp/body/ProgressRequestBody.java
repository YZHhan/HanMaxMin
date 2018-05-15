package com.han.hanmaxmin.hantext.okhttp.body;

import android.app.DownloadManager;
import android.support.annotation.Nullable;

import com.han.hanmaxmin.hantext.okhttp.response.IResponseHandler;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/12
 * @Description
 */

public class ProgressRequestBody extends RequestBody {
    private IResponseHandler mResponseHandler;
    private RequestBody mRequestBody;
    private CountingSink mCountingSink;

    public ProgressRequestBody(RequestBody requestBody, IResponseHandler responseHandler) {
            this.mRequestBody = requestBody;
            this.mResponseHandler = responseHandler;
    }



    @Nullable
    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        mCountingSink = new CountingSink(sink);
        BufferedSink  bufferedSink = Okio.buffer(mCountingSink);
        mRequestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink{
        private long bytesWritten = 0;
        private long contentLength = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            if (contentLength == 0) {
                //获得contentLength的值，后续不再调用
                contentLength = contentLength();
            }
            bytesWritten += byteCount;

            mResponseHandler.onProgress(bytesWritten, contentLength);
        }
    }
}