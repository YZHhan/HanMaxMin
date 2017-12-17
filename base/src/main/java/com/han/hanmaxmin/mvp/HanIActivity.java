package com.han.hanmaxmin.mvp;

/**
 * @CreateBy Administrator
 * @Date 2017/11/7  10:33
 * @Description
 */

public interface HanIActivity extends HanIView{

    int emptyLayoutId();

    int loadingLayoutId();

    int errorLayoutId();

    boolean isTransparentStatusBar();// 控制 状态栏的

    boolean isBlackIconStatusBar();




}
