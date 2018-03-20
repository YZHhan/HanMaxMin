package com.han.hanmaxmin;

import android.text.TextUtils;

import com.han.hanmaxmin.common.http.HttpBuilder;

import me.drakeet.library.CrashWoodpecker;

/**
 * Created by ptxy on 2018/2/27.
 */

public class HanApplication extends AsApplication {
    /**
     * 每次打正式包，必须把  true改为false
     * true开启日志
     * false关闭日志
     */
    @Override
    public boolean isLogOpen() {
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashWoodpecker.fly().to(this);

    }

    /**
     * 每个Http请求都会调用一下这里。
     * 给Http 设置终端地址
     * 给Http 设置请求头
     */
    @Override
    public void initHttpAdapter(HttpBuilder httpBuilder) {
        httpBuilder.setTerminal("http://api.dev.grapedu.cn/api/v1.5");
        httpBuilder.addHeader("Content-Type", "application/json");
        httpBuilder.addHeader("versioncode","8");
        httpBuilder.addHeader("channel", "GRAPE");
        httpBuilder.addHeader("platform", "android");
        httpBuilder.addHeader("Authorization", "");
    }

    /**
     * 页面为空的布局
     */
    @Override
    public int emptyLayoutId() {
        return R.layout.view_empty_layout;
    }

    /**
     * 页面加载的布局
     */
    @Override
    public int loadingLayoutId() {
        return R.layout.view_load_layout;    }

    /**
     * 页面错误的布局
     */
    @Override
    public int errorLayoutId() {
        return R.layout.view_error_layout;
    }
}
