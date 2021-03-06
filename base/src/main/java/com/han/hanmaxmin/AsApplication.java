package com.han.hanmaxmin;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;

import com.han.hanmaxmin.common.http.HttpBuilder;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.widget.dialog.HanProgressDialog;

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

    public abstract void initHttpAdapter(HttpBuilder httpBuilder);

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
    public HanProgressDialog getCommonProgressDialog() {
        return null;
    }

    public @LayoutRes int loadingLayoutId() {
        return 0;
    }

    public @LayoutRes int emptyLayoutId() {
        return 0;
    }

    public @LayoutRes int errorLayoutId() {return 0;}

    public @DrawableRes int defaultImageHolder(){
        return 0;
    }

    /**
     * 在HanPullListFragment作为Footer的布局，全局使用
     * @return
     */
    public int listFooterLayoutId() {
        return R.layout.han_loading_footer;
    }

    public void onCommonHttpResponse(Response response) {

    }
}
