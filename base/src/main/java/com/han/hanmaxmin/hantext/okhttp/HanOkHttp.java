package com.han.hanmaxmin.hantext.okhttp;


import android.os.Handler;
import android.os.Looper;

import com.han.hanmaxmin.hantext.okhttp.builder.DeleteBuilder;
import com.han.hanmaxmin.hantext.okhttp.builder.DownLoadBuilder;
import com.han.hanmaxmin.hantext.okhttp.builder.GetBuilder;
import com.han.hanmaxmin.hantext.okhttp.builder.PatchBuilder;
import com.han.hanmaxmin.hantext.okhttp.builder.PostBuilder;
import com.han.hanmaxmin.hantext.okhttp.builder.UploadBuilder;

import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/11
 * @Description
 */

public class HanOkHttp {
    public static OkHttpClient mOkHttpClient;
    public static Handler mHandler = new Handler(Looper.getMainLooper());

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * construct
     */
    public HanOkHttp() {
        this(null);
    }

    /**
     * construct
     */
    public HanOkHttp (OkHttpClient okHttpClient){
        if(mOkHttpClient == null){
            synchronized (HanOkHttp.class){
                if(mOkHttpClient == null){
                    if(okHttpClient == null){
                        mOkHttpClient = new OkHttpClient();
                    } else {
                        mOkHttpClient = okHttpClient;
                    }
                }
            }
        }
    }

    /**
     * request Method
     */
    public GetBuilder get(){return new GetBuilder(this);}

    public PostBuilder post(){return new PostBuilder(this);}

    public PatchBuilder patch(){return new PatchBuilder(this);}

    public DeleteBuilder delete(){return new DeleteBuilder(this);}

    public UploadBuilder upload(){return new UploadBuilder(this);}

    public DownLoadBuilder download(){return new DownLoadBuilder(this);}








    /**
     * cancel
     * @param tag
     */
    public void cancel(Object tag){
        Dispatcher dispatcher = mOkHttpClient.dispatcher();
        for (Call call : dispatcher.queuedCalls()){
            if(tag.equals(call.request().tag())){
                call.cancel();
            }
        }

        for (Call call : dispatcher.runningCalls()){
            if(tag.equals(call.request().tag())){
                call.cancel();
            }
        }
    }

    public void cancelAll(){
        Dispatcher dispatcher = mOkHttpClient.dispatcher();
        for (Call call : dispatcher.queuedCalls()){
                call.cancel();
        }

        for (Call call : dispatcher.runningCalls()){
                call.cancel();
        }
    }

}
