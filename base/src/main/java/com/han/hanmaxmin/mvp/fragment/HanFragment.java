package com.han.hanmaxmin.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.EventLog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.constants.HanConstants;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.utils.PresenterUtils;
import com.han.hanmaxmin.common.widget.dialog.HanProgressDialog;
import com.han.hanmaxmin.hantext.aspect.intent.Intent;
import com.han.hanmaxmin.mvp.HanIABActivity;
import com.han.hanmaxmin.mvp.HanIActivity;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * @CreateBy Administrator
 * @Date 2017/11/14  15:41
 * @Description Fragment 的父类。
 * ViewAnimator  是FrameLayout的一个子类，用来做View的切换。它是一个变换控件的元素，帮助我们的View之间(如TextView  和  ImageView和其他的Layout)
 * 添加变换。他属于在屏幕VIew添加动画。ViewAnimator可以在两个以及以上Views上平滑的切换，通过合适的动画，提供一个View到另一个View变换的方式。
 *
 *
 *                  ViewAnimator的实现步骤。
 *    1.通过findViewById方法获取ViewAnimator引用，或者动态创建一个实例
 *    2.使用switcherid.addView()方法在ViewAnimator添加子Views
 *    3.使用switcherid.setInAnimation()设置进入动画
 *    4.使用switcherid.setOutAnimation()设置退出动画
 *
 *
 */

public abstract class HanFragment<P extends HanPresenter> extends Fragment implements HanIFragment, View.OnTouchListener {
    private P presenter;
    private boolean hasInitData;
    private int backgroundColorId;
    protected HanProgressDialog mProgressDialog;
    protected ViewAnimator mViewAnimator;

    /**
     * Get the name of the current class as a TAG
     * @return Class TAG
     */
    @Override public String initTag() {
        return getClass().getSimpleName();
    }

    /**
     * Set the current Activity of the title.
     * @param value
     */
    @Override public void setActivityTitle(Object value) {
        setActivityTitle(value,-1);
    }

    @Override public void setActivityTitle(Object value, int code) {
        FragmentActivity activity = getActivity();
        if (activity instanceof HanIABActivity){
            ((HanIABActivity) activity).setActivityTitle(value, code);
            L.i(initTag(),"setActivityTitle()  title="+String.valueOf(value)+" code="+code);
        }
    }

    @Override public void onActionBar() {

    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//添加菜单选项
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        View rootView=initView(inflater);//初始化View     Empty  Loading  Error  Layout
        //  此处省略  一个ViewBind的初始化。
        rootView.setOnTouchListener(this);//  给ViewAnimator  设置监听。
        if(isOpenEventBus() && !EventBus.getDefault().isRegistered(this))EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isDelayDate()){
            hasInitData = true;
            initData(savedInstanceState);//  initData 是对下面子类所暴露的API   进行初始化等等。
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        onActionBar();//
    }

    /**
     * 在View销毁的时候，会对Presenter请求的cancel 制空Presenter  解除EventBus
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(presenter != null){
            presenter.setDetach();
            presenter = null;
        }
        if(isOpenEventBus() && EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
    /**
     * LayoutInflater  的  inflater方法，第一个参数是xml布局，第二个参数是返回的父布局控件。
     * @param inflater
     * @return
     */
    protected  View initView(LayoutInflater inflater){
        View view;
        if(isOpenViewState() && loadingLayoutId() > 0 && emptyLayoutId() > 0 && errorLayoutId() > 0){
            view=inflater.inflate(rootViewLayoutId(),null);//  rootView  本身就是以ViewAnimator 为父布局
            mViewAnimator= (ViewAnimator) view.findViewById(android.R.id.home);
            inflater.inflate(loadingLayoutId(),mViewAnimator);
            View contentView = inflater.inflate(layoutId(), null);
            inflater.inflate(emptyLayoutId(),mViewAnimator);
            inflater.inflate(errorLayoutId(),mViewAnimator);
            if(getBackgroundColorId() > 0)contentView.setBackgroundColor(HanHelper.getInstance().getColor(getBackgroundColorId()));// 考虑到性能优化，默认Fragment无背景
            initDefaultView();
        }else {
            view=inflater.inflate(layoutId(),null);
            if(getBackgroundColorId() > 0)view.setBackgroundColor(HanHelper.getInstance().getColor(getBackgroundColorId()));
        }
        return view;
    }


    protected int rootViewLayoutId() {
        return R.layout.han_fragment_state;
    }



    @Override public P getPresenter() {
        if(presenter==null){
            synchronized (this){
                if(presenter==null){
                    presenter= PresenterUtils.createPresenter(this);
                    L.i(initTag(), "Presenter初始化完成。。。");
                }
            }
        }
        return presenter;
    }

    private void initDefaultView(){
        if(mViewAnimator != null && mViewAnimator.getChildCount() >= 4){
            setDefaultViewClickListener(mViewAnimator.getChildAt(0));
            setDefaultViewClickListener(mViewAnimator.getChildAt(2));
            setDefaultViewClickListener(mViewAnimator.getChildAt(3));

        }
    }

    /**
     * 设置默认View，事件 。   返回键和重新加载
     * @param view
     */
    private  void setDefaultViewClickListener(View view){
        if(view != null){
            View backView = view.findViewById(R.id.han_back_in_default_view);//默认返回键
            if(backView != null){
                if(isShowBackButtonInDefaultView()){
                    backView.setVisibility(View.VISIBLE);
                    backView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().onBackPressed();
                        }
                    });
                } else {
                    backView.setVisibility(View.GONE);
                }
            }

            View reloadView = view.findViewById(R.id.han_reload_in_default_view);//重新加载
            if(reloadView != null){
                reloadView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoadingView();
                        initData(getArguments());//initData 是对下面子类所暴露的API   进行初始化等等。
                    }
                });
            }
        }
    }

    @Override public void initDataWhenDelay() {
        if(!hasInitData && isDelayDate()){
            initData(getArguments());//  initData 是对下面子类所暴露的API   进行初始化等等。
            hasInitData=true;
        }
    }

    @Override public boolean isOpenEventBus() {
        return false;
    }

    @Override public boolean isOpenViewState() {
        return true;
    }

    @Override public boolean isDelayDate() {
        return false;
    }

    @Override public void activityFinish() {
        activityFinish(false);
    }

    @Override public void activityFinish(boolean finishAfterTransition) {
        if(finishAfterTransition){
            ActivityCompat.finishAfterTransition(getActivity());
        }else{
            getActivity().finish();
        }
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

    /**
     * 全局loading  加载  Dialog的是从Application里面得到的， 在提交  提高load的扩展性
     */
    @Override
    public void loading() {
        loading(true);
    }

    @Override
    public void loading(boolean cancelAble) {
        loading(getString(R.string.loading), cancelAble);
    }

    @Override
    public void loading(String message) {
        loading(message,true);
    }

    @ThreadPoint(ThreadType.MAIN)
    @Override
    public void loading(String message, boolean cancelAble) {
        if(mProgressDialog == null) mProgressDialog = HanHelper.getInstance().getApplication().getCommonProgressDialog();
        if(mProgressDialog != null) {
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(cancelAble);
            HanHelper.getInstance().commitDialogFragment(mProgressDialog);
        } else {
            L.e(initTag(), "you should override the method 'Application.getCommonProgressDialog' and return a dialog when called the method : loading(...)");
        }
    }

    @ThreadPoint(ThreadType.MAIN)
    @Override
    public void loadingClose() {
        if(mProgressDialog != null) mProgressDialog.dismissAllowingStateLoss();
    }

    @Override
    public void showLoadingView() {setViewState(HanConstants.VIEW_STATE_LOADING);}

    @Override
    public void showContentView() {setViewState(HanConstants.VIEW_STATE_CONTENT);}

    @Override
    public void showEmptyView() {setViewState(HanConstants.VIEW_STATE_EMPTY);}

    @Override
    public void showErrorView() {setViewState(HanConstants.VIEW_STATE_ERROR);}

    @Override
    public int currentViewState() {
        if(isOpenViewState() && mViewAnimator != null){
            return mViewAnimator.getDisplayedChild();
        }
        return -1;
    }

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

    @Override
    public void intent2Activity(Class clazz, Bundle bundle, int requestCode, ActivityOptionsCompat optionsCompat) {
        FragmentActivity activity = getActivity();
        if(clazz != null && activity != null){
            android.content.Intent intent = new android.content.Intent();
            intent.setClass(activity, clazz);
            if(bundle != null)intent.putExtras(bundle);
            if(optionsCompat == null){
                if(requestCode > 0){
                    startActivityForResult(intent, requestCode);
                } else {
                    startActivity(intent);
                }
            } else {
                if(requestCode > 0 ){
                    ActivityCompat.startActivityForResult(activity, intent, requestCode, optionsCompat.toBundle());
                } else {
                    ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
                }
            }
        }
    }

    @Override
    public void commitFragment(Fragment fragment) {
        commitFragment(fragment, fragment.getClass().getSimpleName());
    }

    @Override
    public void commitFragment(Fragment fragment, String tag) {
        commitFragment(android.R.id.custom, fragment, tag);
    }

    @Override
    public void commitFragment(int layoutId, Fragment fragment) {
        commitFragment(layoutId, fragment, fragment.getClass().getSimpleName());
    }

    @ThreadPoint(ThreadType.MAIN)
    @Override
    public void commitFragment(int layoutId, Fragment fragment, String tag) {
        HanHelper.getInstance().commitFragment(getFragmentManager(), layoutId, fragment, tag);
    }

    @Override
    public void commitFragment(Fragment oldFragment, Fragment fragment) {
    commitFragment(oldFragment, fragment, fragment.getClass().getSimpleName());
    }

    @Override
    public void commitFragment(Fragment oldFragment, Fragment fragment, String tag) {
        commitFragment(oldFragment,  android.R.id.custom, fragment, fragment.getClass().getSimpleName());
    }

    @Override
    public void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment) {
        commitFragment(oldFragment, layoutId, fragment, fragment.getClass().getSimpleName());
    }

    @ThreadPoint(ThreadType.MAIN)
    @Override
    public void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment, String tag) {
        HanHelper.getInstance().commitFragment(getFragmentManager(),oldFragment, layoutId, fragment, tag);
    }

    @Override
    public void commitBackStackFragment(Fragment fragment) {
        commitBackStackFragment(fragment, fragment.getClass().getSimpleName());
    }

    @Override
    public void commitBackStackFragment(Fragment fragment, String tag) {
            commitBackStackFragment(android.R.id.custom, fragment, tag);
    }

    @Override
    public void commitBackStackFragment(int layoutId, Fragment fragment) {
        commitBackStackFragment(layoutId, fragment, fragment.getClass().getSimpleName());
    }

    @ThreadPoint(ThreadType.MAIN)
    @Override
    public void commitBackStackFragment(int layoutId, Fragment fragment, String tag) {
        HanHelper.getInstance().commitBackStackFragment(getFragmentManager(), layoutId, fragment,tag);
    }



    /**
     * 设置View的状态  最后loading和initData
     * @param showState
     */
    @ThreadPoint(ThreadType.MAIN)private void setViewState(final int showState){
        L.i(initTag(), "setViewState()  showState = "+ showState);
        if(!isOpenViewState()){
            L.i(initTag(), "当前Fragment没有打开模式！ isOpenVIewState（） = false");
            return;
        }

        if(mViewAnimator == null){
            return;
        }

        int displayedChild = mViewAnimator.getDisplayedChild();//返回当前View的索引。

        if(displayedChild == showState){// 本身就是  当前View。
            return;
        }

        mViewAnimator.setDisplayedChild(showState);

        if(showState == HanConstants.VIEW_STATE_ERROR){
            mViewAnimator.getCurrentView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoadingView();
                    initData(getArguments());
                }
            });
        }
    }

    public void setBackgroundColorId(@ColorRes int colorId){
        this.backgroundColorId=colorId;
    }

    @Override public int getBackgroundColorId() {
        return backgroundColorId;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return shouldInterceptTouchEvent();
    }

    @Override
    public boolean shouldInterceptTouchEvent() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean isShowBackButtonInDefaultView() {
        return false;
    }

}
