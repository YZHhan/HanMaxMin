package com.han.hanmaxmin;

import android.app.Activity;
import android.app.Application;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;

import okhttp3.Response;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  11:05
 * @Description
 */

public abstract class AsApplication extends Application{
    @Override public void onCreate() {
        super.onCreate();
        if (isLogOpen()) L.init(true);
        HanHelper.getInstance().init(this);
    }

    public abstract boolean isLogOpen();


    public void onActivityCreate(Activity activity) {
    }

    public void onActivityStart(Activity activity) {
    }

    public void onActivityResume(Activity activity) {
    }

    public void onActivityPause(Activity activity) {
    }

    public void onActivityStop(Activity activity) {
    }

    public void onActivityDestroy(Activity activity) {
    }

    /**
     * 公共progressDialog
     */
//    public QsProgressDialog getCommonProgressDialog() {
//        return null;
//    }

    public int loadingLayoutId() {
        return 0;
    }

    public int emptyLayoutId() {
        return 0;
    }

    public int errorLayoutId() {return 0;}

//    public int listFooterLayoutId() {
//        return R.layout.qs_loading_footer;
//    }

    public void onCommonHttpResponse(Response response) {
    }
}
