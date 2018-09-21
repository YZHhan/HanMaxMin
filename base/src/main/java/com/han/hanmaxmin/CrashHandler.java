package com.han.hanmaxmin;

import android.content.Context;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.widget.toast.HanToast;


/*
 * @Author yinzh
 * @Date   2018/6/26 11:26
 * @Description：Crash 抓取
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String  TAG = "CrashHandler";

    private static CrashHandler crashHandler = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    private CrashHandler() {

    }

    public static CrashHandler getInstance(){
        return crashHandler;
    }

    public void init(Context context){
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(mDefaultCrashHandler);
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        HanToast.show("111111");
        L.i(TAG, "我是全局的异常信息"+ throwable.getMessage());
    }
}
