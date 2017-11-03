package com.han.hanmaxmin.common.utils;

import com.han.hanmaxmin.AsApplication;
import com.han.hanmaxmin.common.utils.helper.CacheHelper;
import com.han.hanmaxmin.common.utils.helper.ImageHelper;
import com.han.hanmaxmin.common.utils.helper.ScreenHelper;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  14:03
 * @Description 帮助类中心
 */

public class HanHelper {
    private static final String TAG="HanHelper";
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



}
