package com.han.hanmaxmin.mvp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;

/**
 * @CreateBy Administrator
 * @Date 2017/11/6  18:26
 * @Description  Activity的，顶层抽象
 * 此接口，是Activity的最总父类
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
     *结束Activity
     */
    void activtyFinish();

    void activtyFinish(boolean finishAfterTransition);

    /**
     * loading 状态
     */
    void loading();

    void loading(String message);

    void loading(boolean cancelAble);

    void loading(String message,boolean cancelAble);

    void loadingClose();

    /**
     * 展示某种View
     */
    void showLoadingView();

    void showEmptyView();

    void showErrorView();

    void showContentView();

    int showCurrentViewState();



    void intent2Activity(Class clazz);

    void intent2Activity(Class clazz,int requestCode);

    void intent2Activity(Class clazz,Bundle bundle);

    void intent2Activity(Class clazz,Bundle bundle,ActivityOptionsCompat optionsCompat);

    void intent2Activity(Class clazz,Bundle bundle, int requestCode, ActivityOptionsCompat optionsCompat);

    void commitFragment(Fragment fragment);

    void commitFragment(Fragment fragment,String trg);

    void commitFragment(int layoutId ,Fragment fragment);

    void commitFragment(int layoutId ,Fragment fragment,String trg);

    void commitFragment(Fragment oldFragment, Fragment fragment);

    void commitFragment(Fragment oldFragment, Fragment fragment, String trg);

    void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment);

    void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment,String trg);

    void commitBackStackFragment(Fragment fragment);

    void commitBackStackFragment(Fragment fragment,String trg);

    void commitBackStackFragment(int layoutId ,Fragment fragment);

    void commitBackStackFragment(int layoutId ,Fragment fragment,String trg);
}
