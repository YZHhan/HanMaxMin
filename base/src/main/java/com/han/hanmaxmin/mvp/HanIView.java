package com.han.hanmaxmin.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityOptionsCompat;

/**
 * @CreateBy Administrator
 * @Date 2017/11/6  18:26
 * @Description  View  顶层抽象
 * 此接口，是Activity的最终父类
 *
 * 封装特性：
 *  1.首先的有P层的接入，因为是Activity的最终父类。
 *  2.既然是Activity 的有跳转的封装。A——A  A——F
 *  3.Acivity的各种View的显示，{error、laod、empty、content———一个控制View显示的状态}
 *  4.eventBud——控制
 *  5.load ——控制
 *  初始化数据
 *
 *
 */

public interface HanIView<P> {
    /**
     *Log 输出的  TAG
     */
    String initTag();

    /**
     * XML
     */
    int layoutId();

    /**
     * 初始化数据
     */
    void initData(Bundle savedInstanceState);

    /**
     * 过期数据
     */
    boolean isDelayDate();

    /**
     * 初始化数据，当过期的时候
     */
    void initDataWhenDelay();

    /**
     * 得到Presenter
     */
    P getPresenter();

    /**
     * 得到Context的对象。
     */
    Context getContext();

    /**
     * 打开EventBus（开关）
     */
    boolean isOpenEventBus();

    /**
     * 是否打开，errorLayout、emptyLayout、loadingLayout
     */
    boolean isOpenViewState();

    /**
     * 是否是显示  返回按钮在  默认的view中
     */
    boolean isShowBackButtonInDefaultView();

    /**
     *结束Activity
     */
    void activityFinish();

    void activityFinish(boolean finishAfterTransition);

    /**
     * loading 状态
     */
    void loading();

    void loading(String message);

    void loading(boolean cancelAble);

    void loading(@StringRes int resId);

    void loading(@StringRes int resId, boolean cancelAble);

    void loading(String message,boolean cancelAble);

    void loadingClose();

    /**
     * 展示某种View
     */
    void showLoadingView();

    void showEmptyView();

    void showErrorView();

    void showContentView();

    int currentViewState();



    void intent2Activity(Class clazz);

    void intent2Activity(Class clazz,int requestCode);

    void intent2Activity(Class clazz,Bundle bundle);

    void intent2Activity(Class clazz,Bundle bundle,ActivityOptionsCompat optionsCompat);

    void intent2Activity(Class clazz,Bundle bundle, int requestCode, ActivityOptionsCompat optionsCompat);

    void commitFragment(android.support.v4.app.Fragment fragment);

    void commitFragment(android.support.v4.app.Fragment fragment,String tag);

    void commitFragment(int layoutId , android.support.v4.app.Fragment fragment);

    void commitFragment(int layoutId , android.support.v4.app.Fragment fragment,String tag);

    void commitFragment(android.support.v4.app.Fragment oldFragment, android.support.v4.app.Fragment fragment);

    void commitFragment(android.support.v4.app.Fragment oldFragment, android.support.v4.app.Fragment fragment, String tag);

    void commitFragment(android.support.v4.app.Fragment oldFragment, int layoutId, android.support.v4.app.Fragment fragment);

    void commitFragment(android.support.v4.app.Fragment oldFragment, int layoutId, android.support.v4.app.Fragment fragment,String tag);

    void commitBackStackFragment(android.support.v4.app.Fragment fragment);

    void commitBackStackFragment(android.support.v4.app.Fragment fragment,String tag);

    void commitBackStackFragment(int layoutId , android.support.v4.app.Fragment fragment);

    void commitBackStackFragment(int layoutId , android.support.v4.app.Fragment fragment,String tag);
}
