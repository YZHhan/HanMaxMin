package com.han.hanmaxmin;

import com.han.hanmaxmin.common.http.HttpBuilder;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.hantext.aliyun.VideoHelper;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  15:37
 * @Description
 */

public class HanMacMinApplication extends AsApplication {
    private final String businessId="";

    @Override public boolean isLogOpen() {
        return true;
    }

    @Override public void initHttpAdapter(HttpBuilder builder) {

    }

    @Override public void onCreate() {
        super.onCreate();
        HanHelper.getInstance().init(this);
        VideoHelper.getInstance().init(this,"");
    }
}
