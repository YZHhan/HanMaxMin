package com.han.hanmaxmin;

import com.han.hanmaxmin.common.utils.HanHelper;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  15:37
 * @Description
 */

public class HanMacMinApplication extends AsApplication {
    @Override public boolean isLogOpen() {
        return true;
    }

    @Override public void onCreate() {
        super.onCreate();

        HanHelper.getInstance().init(this);
    }
}
