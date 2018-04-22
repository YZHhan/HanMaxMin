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
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ViewAnimator;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.constants.HanConstants;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.utils.PresenterUtils;
import com.han.hanmaxmin.common.widget.dialog.HanProgressDialog;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @CreateBy Administrator
 * @Date 2017/11/7  10:36
 * @Description Activity 的顶层父类     定位抽象类，泛型P继承P层父类。本抽象继承所属Activity和实现Activity的抽象
 * 作用：Activity的封装
 * 封装：
 * 1.Presenter的泛型
 * 2.继承FragmentActivity（项目的所属Activity）
 * 3.实现抽象类 HanActivity，（Activity的抽象的父类）
 * 4.方法实现类
 * <p>
 * <p>
 * ViewAnimator:
 * getDisplayedChild():返回当前显示的子View
 */

public abstract class HanActivity<P extends HanPresenter> extends FragmentActivity implements HanIActivity {
    private                        P                 presenter;
    private                        HanProgressDialog mProgressDialog; //一个Dialog
    private                        ViewAnimator      mViewAnimator;
    private                        boolean           hasInitData;


    @Override public String initTag() {
        return getClass().getSimpleName();
    }

    /**
     * 次布局的background为null   解决过度绘制。。
     *
     * @return
     */
    @Override public int layoutId() {
        return R.layout.han_framelayout;//再次写一个布局
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //入栈 。很必要的一个动作.只有在顶层父类入栈，才能后面的一系列操作
        HanHelper.getInstance().getScreenHelper().pushActivity(this);
        HanHelper.getInstance().getApplication().onActivityCreate(this);
        initStatusBar();//状态栏 。。。
        View view = initView();
        setContentView(view);
        //一行代码，自定义 封装 View  Bind   有关
//        HanHelper.getInstance().
        if (isOpenEventBus() && EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        if (!isDelayDate()) {//
            hasInitData = true;
            initData(savedInstanceState);
        }
    }

    /**
     * 状态栏，改变  状态栏透明
     */
    private void initStatusBar() {
        if (isTransparentStatusBar()) {//是否是状态栏透明。。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isBlackIconStatusBar()) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window window = getWindow();
                WindowManager.LayoutParams winParams = window.getAttributes();
                int status = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                winParams.flags |= status;
                window.setAttributes(winParams);
            } else {
                L.e(initTag(), "当前Android SDK版本太低(" + Build.VERSION.SDK_INT + "),只要SDK版本 >= KITKAT才支持透明状态栏， 推荐在actionbarLayout() 方法中根据该条件给出不同高度的布局");
            }
        } else {
            if (isBlackIconStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
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

    @Override protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.setDetach();
            presenter = null;
        }
        if (isOpenEventBus() && EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        HanHelper.getInstance().getApplication().onActivityDestroy(this);
        HanHelper.getInstance().getScreenHelper().popActivity(this);
    }


    /**
     * 初始化：loadingLayoutId、emptyLayoutId、errorLayoutId，有isOpenViewState控制
     * View的infalter 方法。将rootViewLayout作为一个View。然后在findVIewByID到ViewAnimator
     * 把ViewAnimator作为跟布局将各种类型的布局加进去。
     */
    protected View initView() {
        View rootView;
        if (isOpenViewState() && loadingLayoutId() > 0 && emptyLayoutId() > 0 && errorLayoutId() > 0) {
            rootView = View.inflate(this, rootViewLayoutId(), null);
            mViewAnimator = (ViewAnimator) rootView.findViewById(android.R.id.home);
            //表示将ViewAnimator作为跟布局，将layoutid 添加进去。
            View.inflate(this, loadingLayoutId(), mViewAnimator);
            View.inflate(this, layoutId(), mViewAnimator);
            View.inflate(this, errorLayoutId(), mViewAnimator);
            View.inflate(this, emptyLayoutId(), mViewAnimator);
        } else {
            rootView = View.inflate(this, layoutId(), null);
        }
        return rootView;
    }

    /**
     * ViewAnimator
     */
    protected int rootViewLayoutId() {
        return R.layout.han_activity_ab_state;
    }


    @Override public P getPresenter() {
        if (presenter == null) {
            synchronized (this) {
                if (presenter == null) {
                    presenter= PresenterUtils.createPresenter(this);
                    if(presenter == null){
                        L.i(initTag(), "当前的Presenter为空，，，，，");
                    }
                    L.i(initTag(), "Presenter 初始化完成.....");
                }
            }
        }
        return presenter;
    }

    @Override public void initDataWhenDelay() {
        if (!hasInitData && isDelayDate()) {
            initData(getIntent().getExtras());
            hasInitData = true;
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
        if (finishAfterTransition) {
            ActivityCompat.finishAfterTransition(this);
        } else {
            finish();
        }
    }

    /*_____________________________________________________在Application里面操作全局的布局————————————————*/
    //---------------------------------------在这里调用Application的loading和empty和error。。。才能将Activity的布局联系在Application中。
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

    @Override
    public void loading(int resId) {
        loading(resId, true);
    }

    @Override
    public void loading(int resId, boolean cancelAble) {
        loading(getResources().getString(resId), cancelAble);
    }

    //需要放在主线程 一个注解
    @ThreadPoint(ThreadType.MAIN) @Override public void loading(String message, boolean cancelAble) {
        if (mProgressDialog == null) mProgressDialog = HanHelper.getInstance().getApplication().getCommonProgressDialog();
        if (mProgressDialog != null) {
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(cancelAble);
//                HanHelper.getInstance()
        } else {
            L.e(initTag(), "you shoud the method 'Application.getCommonProgressDialog' and return a dialog when called the method :loading(...) ");
        }
    }



    /**
     * 状态栏，控制。。。。
     */
    @Override public boolean isTransparentStatusBar() {
        return false;
    }

    @Override public boolean isBlackIconStatusBar() {
        return false;
    }



    @ThreadPoint(ThreadType.MAIN) @Override public void loadingClose() {
        if (mProgressDialog != null) mProgressDialog.dismissAllowingStateLoss();
    }

    @Override public void showLoadingView() {
        setViewState(HanConstants.VIEW_STATE_LOADING);
    }

    @Override public void showContentView() {
        setViewState(HanConstants.VIEW_STATE_CONTENT);
    }

    @Override public void showEmptyView() {
        setViewState(HanConstants.VIEW_STATE_EMPTY);
    }

    @Override public void showErrorView() {
        setViewState(HanConstants.VIEW_STATE_ERROR);
    }


    @ThreadPoint(ThreadType.MAIN) private void setViewState(int showState) {
        L.i(initTag(), "setViewState()   showState=" + showState);
        if (!isOpenViewState()) {
            L.i(initTag(), "The  current activity 没有打开状态模式 ！isOpenViewState()=false");
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
//        if (showState == HanConstants.VIEW_STATE_ERROR) {
//            mViewAnimator.getCurrentView().setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    showLoadingView();
//                    initData(getIntent().getExtras());
//                }
//            });
//        }
    }

    @Override
    public int currentViewState() {
        if(isOpenViewState() && mViewAnimator != null){
            return mViewAnimator.getDisplayedChild();
        }
        return -1;
    }


//    _________________________________________________________________________  跳转


    @Override
    public void intent2Activity(Class clazz) {
        intent2Activity(clazz, null, 0, null);
    }

    @Override
    public void intent2Activity(Class clazz, Bundle bundle) {
        intent2Activity(clazz, bundle, 0, null);
    }

    @Override
    public void intent2Activity(Class clazz, int requestCode) {
        intent2Activity(clazz, null, requestCode, null);
    }

    @Override
    public void intent2Activity(Class clazz, Bundle bundle, ActivityOptionsCompat optionsCompat) {
        intent2Activity(clazz, bundle, 0, optionsCompat);
    }

    /**
     * Androdi 5.0 新增跳转动画，为了兼容低版本。所以使用ActivityOptionsCompat。。
     */
    @Override
    public void intent2Activity(Class clazz, Bundle bundle, int requestCode, ActivityOptionsCompat optionsCompat) {
        if(clazz !=null){
            Intent intent=new Intent();
            intent.setClass(this, clazz);
            if (bundle != null)intent.putExtras(bundle);
            if(optionsCompat == null){
                if(requestCode > 0){
                    startActivityForResult(intent, requestCode);
                }else {
                    startActivity(intent);
                }
            } else {
                if(requestCode > 0){
                    ActivityCompat.startActivityForResult(this, intent, requestCode, optionsCompat.toBundle());
                } else {
                    ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
                }
            }
        }
    }


    @Override
    public void commitFragment(Fragment fragment) {
        commitFragment(fragment, fragment.getClass().getSimpleName());
    }

    @Override
    public void commitFragment(Fragment fragment, String trg) {
        commitFragment(android.R.id.custom, fragment, trg);
    }

    @Override
    public void commitFragment(int layoutId, Fragment fragment) {
        commitFragment(layoutId, fragment, fragment.getClass().getName());
    }

    /**
     * 最终是由HanHelper来进行的
     * @param layoutId
     * @param fragment
     * @param trg
     */
    @Override
    public void commitFragment(int layoutId, Fragment fragment, String trg) {
        HanHelper.getInstance().commitFragment(getSupportFragmentManager(), layoutId, fragment, trg);
    }

    @Override
    public void commitFragment(Fragment oldFragment, Fragment fragment) {
        commitFragment(oldFragment, fragment, fragment.getClass().getSimpleName());
    }

    @Override
    public void commitFragment(Fragment oldFragment, Fragment fragment, String trg) {
        commitFragment(oldFragment, android.R.id.custom, fragment, trg);
    }

    @Override
    public void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment) {
    commitFragment(oldFragment, layoutId, fragment, fragment.getClass().getSimpleName());
    }

    @Override
    public void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment, String trg) {
HanHelper.getInstance().commitFragment(getSupportFragmentManager(), oldFragment, layoutId, fragment, trg);
    }
    @Override
    public void commitBackStackFragment(Fragment fragment) {
        commitFragment(fragment, fragment.getClass().getSimpleName());

    }

    @Override
    public void commitBackStackFragment(Fragment fragment, String trg) {
        commitFragment(android.R.id.custom, fragment, trg);
    }

    @Override
    public void commitBackStackFragment(int layoutId, Fragment fragment) {
        commitFragment(layoutId, fragment, fragment.getClass().getSimpleName());
    }

    @Override
    public void commitBackStackFragment(int layoutId, Fragment fragment, String trg) {
        HanHelper.getInstance().commitBackStackFragment(getSupportFragmentManager(), layoutId, fragment, trg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @ThreadPoint(ThreadType.MAIN) @Override public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 将onKeyDown事件传递到当前展示的Fragement
     *
     * @param keyCode
     * @param event
     * @return
     * @Annotation @SuppressLint（"NewApi"）作用是屏蔽Androdi lint 错误。在Androdi代码中，有时候会使用在AndroidManifest中设置的android：minSdkVersion版本更高的方法。
     */
    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        @SuppressLint("RestrictedApi") List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null && fragmentList.isEmpty()) {
            int size = fragmentList.size();
            for (int i = size - 1; i >= 0; i--) {
                Fragment fragment = fragmentList.get(i);
                if (fragment != null && !fragment.isDetached() && fragment.isResumed() && fragment.isAdded() && fragment instanceof Fragment) {//缺少一个Fragmetn的封装
                    L.i(initTag(), "onKeyDown... Fragment:" + fragment.getClass().getSimpleName() + "  isDetached:" + fragment.isDetached() + " isAdded:" + fragment.isAdded() + " isResumed：" + fragment.isResumed());


                }
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}


