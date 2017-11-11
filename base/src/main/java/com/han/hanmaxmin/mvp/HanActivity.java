package com.han.hanmaxmin.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ViewAnimator;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * @CreateBy Administrator
 * @Date 2017/11/7  10:36
 * @Description Activity 的顶层抽象类
 */

public abstract class HanActivity<P extends HanPresenter> extends FragmentActivity implements HanIActivity {
    private P presenter;
    //一个Dialog

    private ViewAnimator mViewAnimator;
    private boolean      hasInitData;


    @Override public String initTag() {
        return getClass().getSimpleName();
    }

    @Override public int layoutId() {
        return R.layout.han_framelayout;//再次写一个布局
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //入栈 。很必要的一个动作.只有在顶层父类入栈，才能后面的一系列操作
        HanHelper.getInstance().getScreenHelper().pushActivity(this);
        HanHelper.getInstance().getApplication().onActivityCreate(this);
        View view = initView();
        setContentView(view);
        //一行代码，跟ButterKnife  有关
        //
        if (isOpenEventBus() && EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        if (!isDelayDate()) {//
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

    @Override protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
//            presenter.   销毁Presenter
            //销毁EventBus
            if (isOpenEventBus() && EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        }
        HanHelper.getInstance().getApplication().onActivityDestroy(this);
    }


    protected View initView() {
        View rootView;
        if (isOpenViewState() && layoutId() > 0 && emptyLayoutId() > 0 && errorLayoutId() > 0) {
            rootView = View.inflate(this, R.layout.han_activity_state, null);
            mViewAnimator = (ViewAnimator) rootView.findViewById(android.R.id.home);//Android ActionBar的左键
            View.inflate(this, loadingLayoutId(), mViewAnimator);
            View.inflate(this, layoutId(), mViewAnimator);
            View.inflate(this, errorLayoutId(), mViewAnimator);
            View.inflate(this, emptyLayoutId(), mViewAnimator);
        } else {
            rootView = View.inflate(this, layoutId(), null);
        }
        return rootView;
    }

    @Override public P getPresenter() {
        if (presenter == null) {
            synchronized (this) {
                if (presenter == null) {
//                    presenter= PresenterUtils.crea  创造Presenter
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

    @Override public void activtyFinish() {
        activtyFinish(false);
    }

    @Override public void activtyFinish(boolean finishAfterTransition) {
        if (finishAfterTransition) {
            ActivityCompat.finishAfterTransition(this);
        } else {
            finish();
        }
    }

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
        loading(getString(R.string.loading), true);
    }

    @Override public void loading(String message) {
        loading(message, true);
    }
    //需要放在主线程
    @Override public void loading(String message, boolean cancelAble) {

    }
}


