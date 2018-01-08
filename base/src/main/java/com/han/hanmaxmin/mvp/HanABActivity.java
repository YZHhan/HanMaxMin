package com.han.hanmaxmin.mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ViewAnimator;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.constants.HanConstants;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.widget.dialog.HanProgressDialog;
import com.han.hanmaxmin.mvp.fragment.HanFragment;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @CreateBy Administrator
 * @Date 2018/1/4  21:31
 * @Description
 */

public abstract class HanABActivity<P extends HanPresenter> extends AppCompatActivity implements HanIABActivity {
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
        setContentView(view);
        //自己封装的  ViewBind
//        HanHelper.getInstance().ge
        //进行EventBus  的注册。
        if (isOpenEventBus() && !EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        if (!isDelayDate()) {
            hasInitData = true;
            initData(savedInstanceState);
        }
    }

    @Override protected void onStart() {
        super.onStart();
        HanHelper.getInstance().getApplication().onActivityStart(this);
    }

    @Override protected void onResume() {
        super.onResume();
        HanHelper.getInstance().getApplication().onActivityResume(this);
    }

    @Override protected void onPause() {
        super.onPause();
        HanHelper.getInstance().getApplication().onActivityPause(this);
    }

    @Override protected void onStop() {
        super.onStop();
        HanHelper.getInstance().getApplication().onActivityStop(this);
    }

    /**
     * 重新赋值 Presenter的对象为null  在把EventBus给接触注册
     */
    @Override protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {//  对Presenter  进行clear
//            presenter.setD
            presenter = null;
        }
        if (isOpenEventBus() && EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        HanHelper.getInstance().getApplication().onActivityDestroy(this);
        HanHelper.getInstance().getScreenHelper().popActivity(this);
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
            if (isBlackIconStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果大于等于23  6.0
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
            initDefaultView();//不理解的  代码块
        } else {
            rootView = View.inflate(this, R.layout.han_activity_ab, null);
            mToolBar = (Toolbar) rootView.findViewById(R.id.toolbar);
            ViewGroup mainView = (ViewGroup) rootView.findViewById(android.R.id.home);
            View.inflate(this, layoutId(), mainView);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) mToolBar.setElevation(0);
        if (actionbarLayoutId() > 0) {
            ViewGroup actionbarContainer = (ViewGroup) mToolBar.findViewById(R.id.vg_toolbar);
            View.inflate(this, actionbarLayoutId(), actionbarContainer);
        }
        setSupportActionBar(mToolBar);
        return rootView;
    }

    protected int rootViewLayoutId() {
        return R.layout.han_activity_ab_state;
    }


    private void initDefaultView() {
        if (mViewAnimator != null && mViewAnimator.getChildCount() >= 4) {

        }
    }

    @Override public Context getContext() {
        return this;
    }

    @Override public boolean isOpenEventBus() {
        return false;
    }

    @Override public boolean isOpenViewState() {
        return false;
    }

    @Override public boolean isDelayDate() {
        return false;
    }

    @Override public void activityFinish() {
        activityFinish(false);
    }

    @Override public void activityFinish(boolean finishAfterTransition) {
        if (finishAfterTransition) ActivityCompat.finishAfterTransition(this);
        else finish();
    }

    @Override public boolean isTransparentStatusBar() {
        return false;
    }

    @Override public int loadingLayoutId() {
        return HanHelper.getInstance().getApplication().loadingLayoutId();
    }

    @Override public int emptyLayoutId() {
        return HanHelper.getInstance().getApplication().emptyLayoutId();
    }

    @Override public int errorLayoutId() {
        return HanHelper.getInstance().getApplication().errorLayoutId();
    }

    @Override public void loading() {
        loading(true);
    }

    @Override public void loading(boolean cancelAble) {
        loading(getString(R.string.loading), cancelAble);
    }

    @Override public void loading(String message) {
        loading(message, true);
    }

    @ThreadPoint(ThreadType.MAIN) @Override public void loading(String message, boolean cancelAble) {
        if (mPreogressDialog == null) mPreogressDialog = HanHelper.getInstance().getApplication().getCommonProgressDialog();
        if (mPreogressDialog != null) {
            mPreogressDialog.setmMessage(message);
            mPreogressDialog.setCancelable(cancelAble);
            HanHelper.getInstance().commitDialogFragment(mPreogressDialog);
        } else {
            L.e(initTag(), "You should override the method 'Application.getCommonProgressDialog' and return a dailog when called the method : loading(...)");
        }
    }

    @Override public void loadingClose() {
        if (mPreogressDialog != null) mPreogressDialog.dismissAllowingStateLoss();
    }

    @Override public void showLoadingView() {
        setViewState(HanConstants.VIEW_STATE_LOADING);
    }

    @Override public void showEmptyView() {
        setViewState(HanConstants.VIEW_STATE_EMPTY);
    }

    @Override public void showContentView() {
        setViewState(HanConstants.VIEW_STATE_CONTENT);
    }

    @Override public void showErrorView() {

    }

    @Override public int currentViewState() {
        if (isOpenViewState() && mViewAnimator != null) {
            return mViewAnimator.getDisplayedChild();
        }
        return -1;
    }

    @Override public void intent2Activity(Class clazz) {
        intent2Activity(clazz, null, 0, null);
    }

    @Override public void intent2Activity(Class clazz, int requestCode) {
        intent2Activity(clazz, null, requestCode, null);
    }

    @Override public void intent2Activity(Class clazz, Bundle bundle) {
        intent2Activity(clazz, bundle, 0, null);
    }

    @Override public void intent2Activity(Class clazz, Bundle bundle, ActivityOptionsCompat optionsCompat) {
        intent2Activity(clazz, bundle, 0, optionsCompat);
    }

    @Override public void intent2Activity(Class clazz, Bundle bundle, int requestCode, ActivityOptionsCompat optionsCompat) {
        if (clazz != null) {
            Intent intent = new Intent();
            intent.setClass(this, clazz);
            if (bundle != null) intent.putExtras(bundle);
            if (optionsCompat == null) {
                if (requestCode > 0) {
                    startActivityForResult(intent, requestCode);
                } else {
                    startActivity(intent);
                }
            } else {
                if (requestCode > 0) {
                    ActivityCompat.startActivityForResult(this, intent, requestCode, optionsCompat.toBundle());
                } else {
                    ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
                }
            }
        }
    }

    @Override public void commitFragment(android.support.v4.app.Fragment fragment) {
        commitFragment(fragment, fragment.getClass().getSimpleName());
    }

    @Override public void commitFragment(android.support.v4.app.Fragment fragment, String trg) {
        commitFragment(android.R.id.custom, fragment, trg);
    }

    @Override public void commitFragment(int layoutId, android.support.v4.app.Fragment fragment) {
        commitFragment(layoutId, fragment, fragment.getClass().getSimpleName());
    }

    @Override public void commitFragment(int layoutId, android.support.v4.app.Fragment fragment, String tag) {
        HanHelper.getInstance().commitFragment(getSupportFragmentManager(), layoutId, fragment, tag);
    }

    @Override public void commitFragment(Fragment oldFragment, Fragment fragment) {
        commitFragment(oldFragment, fragment, fragment.getClass().getSimpleName());
    }

    @Override public void commitFragment(Fragment oldFragment, Fragment fragment, String tag) {
        commitFragment(oldFragment, android.R.id.custom, fragment, tag);
    }

    @Override public void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment) {
        HanHelper.getInstance().commitFragment(getSupportFragmentManager(), oldFragment, layoutId, fragment, fragment.getClass().getSimpleName());
    }

    @Override public void commitBackStackFragment(Fragment fragment) {
        commitBackStackFragment(fragment, fragment.getClass().getSimpleName());
    }

    @Override public void commitBackStackFragment(Fragment fragment, String trg) {
        commitBackStackFragment(android.R.id.custom, fragment, trg);
    }

    @Override public void commitBackStackFragment(int layoutId, Fragment fragment) {
        commitBackStackFragment(layoutId, fragment, getClass().getSimpleName());
    }

    @Override public void commitBackStackFragment(int layoutId, Fragment fragment, String trg) {
        HanHelper.getInstance().commitBackStackFragment(getSupportFragmentManager(), layoutId, fragment, fragment.getClass().getSimpleName());
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        PermissionUtils.getInstance().pare
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        @SuppressLint("RestrictedApi") List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null && fragmentList.isEmpty()) {
            int size = fragmentList.size();
            for (int i = size - 1; i > 0; i--) {
                Fragment fragment = fragmentList.get(i);
                if (fragment != null && !fragment.isDetached() && fragment.isResumed() && fragment.isAdded() && fragment instanceof HanFragment) {
                    L.i(initTag(), "onKeyDown... Fragment:" + fragment.getClass().getSimpleName()+"  isDetached: "+((HanFragment) fragment).isDelayDate()+ "isAdded: "+fragment.isAdded() + "  isResumed: "+fragment.isResumed());
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @ThreadPoint(ThreadType.MAIN) public void setViewState(int showState) {
        L.i(initTag(), "setViewState() showState=" + showState);
        if (!isOpenViewState()) {
            L.i(initTag(), "当前Activity 没有打开状态模式! isOpenViewState() = false");
            return;
        }

        if (mViewAnimator == null) {
            return;
        }

        int displayedChild = mViewAnimator.getDisplayedChild();
        if (displayedChild == showState) {
            return;
        }

        mViewAnimator.setDisplayedChild(showState);
        if (showState == HanConstants.VIEW_STATE_ERROR) {
            mViewAnimator.getCurrentView().setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    showLoadingView();
                    initData(getIntent().getExtras());
                }
            });
        }
    }
}
