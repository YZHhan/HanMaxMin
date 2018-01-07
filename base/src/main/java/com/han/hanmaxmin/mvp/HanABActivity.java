package com.han.hanmaxmin.mvp;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ViewAnimator;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.widget.dialog.HanProgressDialog;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * @CreateBy Administrator
 * @Date 2018/1/4  21:31
 * @Description
 */

public abstract class HanABActivity<P extends HanPresenter> extends HanActivity<P> implements HanIABActivity {
    private   P                 presenter;
    protected HanProgressDialog mPreogressDialog;
    private   ViewAnimator      mViewAnimator;
    private   boolean           hasInitData;

    @Override public String initTag() {
        return getClass().getSimpleName();
    }

    @Override public int layoutId() {
        return R.layout.han_framelayout;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HanHelper.getInstance().getScreenHelper().pushActivity(this);
        HanHelper.getInstance().getApplication().onActivityCreate(this);
        initStatusBar();
        View view = ininView();

    }

    /**
     * 控制状态栏的  透明
     */
    protected void initStatusBar() {
        if (isTransparentStatusBar()) {//默认false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//如果大于等于 SDK 21  5.0
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isTransparentStatusBar()) {//如果大于等于 SDK 23  6.0
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {//如果大于等于 SDK 16  4.1    不明白
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//如果大于等于 SDK 19   4.4
                Window window = getWindow();
                WindowManager.LayoutParams winParams = window.getAttributes();
                final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                winParams.flags |= bits;
                window.setAttributes(winParams);
            } else {//此时此刻  是不支持
                L.e(initTag(), "当前Android SDK版本太低 (" + Build.VERSION.SDK_INT + "),只有SDK 版本 >= KITKAT才支持透明状态栏， 推荐actionbarLayoutId()方法中根据该条件给出不同高度的布局");
            }
        } else {
            if(isBlackIconStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//如果大于等于23  6.0
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }
    }

    protected View ininView() {
        View rootView;
        Toolbar mToolBar;
        if (isOpenViewState() && loadingLayoutId() > 0 && emptyLayoutId() > 0 && errorLayoutId() > 0) {
            rootView = View.inflate(this, rootViewLayoutId(), null);
            mViewAnimator = (ViewAnimator) rootView.findViewById(android.R.id.home);
            mToolBar = (Toolbar) rootView.findViewById(R.id.toolbar);
            View.inflate(this, loadingLayoutId(), null);
            View.inflate(this, layoutId(), null);
            View.inflate(this, emptyLayoutId(), null);
            View.inflate(this, errorLayoutId(), null);
            initDefaultView();
        } else {

        }

        return null;
    }


    private void initDefaultView() {
        if (mViewAnimator != null && mViewAnimator.getChildCount() >= 4) {

        }
    }

    @Override public boolean isTransparentStatusBar() {
        return false;
    }
}
