package com.han.hanmaxmin.mvp;

/**
 * @CreateBy Administrator
 * @Date 2017/11/7  10:33
 * @Description  Activity顶层的二级父类  extends HanIView
 *      1.三种状态的布局
 *      2.状态栏的透明控制
 *
 */

public interface HanIActivity extends HanIView{

    int emptyLayoutId();

    int loadingLayoutId();

    int errorLayoutId();

    boolean isTransparentStatusBar();// 控制 状态栏的

    boolean isBlackIconStatusBar();




}
