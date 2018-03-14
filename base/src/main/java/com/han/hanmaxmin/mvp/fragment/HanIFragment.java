package com.han.hanmaxmin.mvp.fragment;

import android.view.KeyEvent;

import com.han.hanmaxmin.mvp.HanIView;

/**
 * @CreateBy Administrator
 * @Date 2017/11/14  15:36
 * @Description  Fragment的顶层抽象接口
 */

public interface HanIFragment extends HanIView {
    /**
     * 结合Activity的Action
     * 使用在onCreateOptionsMenu
     */
    void onActionBar();

    void setActivityTitle(Object value);

    void setActivityTitle(Object value, int code);

    boolean shouldInterceptTouchEvent();

    boolean onKeyDown(int keyCode, KeyEvent event);

    int emptyLayoutId();

    int errorLayoutId();

    int loadingLayoutId();

    int getBackgroundColorId();

}
