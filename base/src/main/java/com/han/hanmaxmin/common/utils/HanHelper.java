package com.han.hanmaxmin.common.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.han.hanmaxmin.AsApplication;
import com.han.hanmaxmin.common.threadpoll.HanThreadPollHelper;
import com.han.hanmaxmin.common.utils.helper.CacheHelper;
import com.han.hanmaxmin.common.utils.helper.ImageHelper;
import com.han.hanmaxmin.common.utils.helper.ScreenHelper;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  14:03
 * @Description 帮助类中心
 */

public class HanHelper {
    private static final String TAG = "HanHelper";
    private static HanHelper helper;

    public static HanHelper getInstance() {
        if (helper == null) {
            synchronized (HanHelper.class) {
                if (helper == null) helper = new HanHelper();
            }
        }
        return helper;
    }

    public AsApplication getApplication() {
        return application;
    }

    private volatile AsApplication application;

    private HanHelper() {
    }

    public void init(AsApplication asApplication) {
        this.application = asApplication;
    }

    public ScreenHelper getScreenHelper() {
        return ScreenHelper.getInstance();
    }

    public CacheHelper getCacheHelper() {
        return CacheHelper.getInstance();
    }

    public ImageHelper getImageHelper() {
        return ImageHelper.getInstance();
    }

    public HanThreadPollHelper getThreadHelper() {
        return HanThreadPollHelper.getInstance();
    }


    public String getString(@StringRes int resId) {
        return getApplication().getString(resId);
    }

    public String getString(@StringRes int resId, Object... formatArgs) {
        return getApplication().getString(resId, formatArgs);
    }

    public Drawable getDrawable(@DrawableRes int resId) {
    return     getApplication().getResources().getDrawable(resId);
    }

    public int getColor(@ColorRes int resId){
        return getApplication().getResources().getColor(resId);
    }

    public float getDimension(@DimenRes int resId){
        return getApplication().getResources().getDimension(resId);
    }


}
