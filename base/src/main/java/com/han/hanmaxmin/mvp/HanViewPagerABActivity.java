package com.han.hanmaxmin.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.widget.viewpager.HanViewPager;
import com.han.hanmaxmin.common.widget.viewpager.PagerSlidingTabStrip;
import com.han.hanmaxmin.mvp.adapter.HanViewPagerAdapter;
import com.han.hanmaxmin.mvp.model.HanModelPager;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * @CreateBy Administrator
 * @Date 2018/1/4  21:30
 * @Description 作为ViewPager和Activity的混合
 * 其实也就是在Activity的布局里面开始写入ViewPager
 */

public abstract class HanViewPagerABActivity<P extends HanPresenter> extends HanABActivity implements HanIViewPagerABActivity {
    private HanViewPagerAdapter adapter;   //ViewPager的adapter的
    private HanViewPager pager;            //自己封装的ViewPager
    private PagerSlidingTabStrip tabs;    //ViewPager的滑动的tab


    @Override
    public int layoutId() {
        return R.layout.han_activity_viewpager;
    }

    @Override
    protected View initView() {
        View view = super.initView();// 调用父类HanABActivity的方法()，
        initViewPager(view);
        return super.initView();
    }

    private   void initViewPager(View view){//初始化布局里的 ViewPager和tab
        pager = (HanViewPager) view.findViewById(R.id.han_viewpager);
        tabs = (PagerSlidingTabStrip) view.findViewById(android.R.id.tabs);
        initTabValues(tabs);//初始化PagerSlidingTabStrip  的属性值和 对外封装
        initViewPager(getModelPagers(), 3);
    }

    @Override
    public void initViewPager(HanModelPager[] modelPagers, int offScreenPageLimit) {
        if (modelPagers != null){
            getPageAdapter(pager, tabs);
        }


    }

    public HanViewPagerAdapter getPageAdapter(HanViewPager pager, PagerSlidingTabStrip tabs) {

        return new ;
    }

    private   void initTabValues(PagerSlidingTabStrip tabs){

    }

    @Override
    public void setActivityTitle(Object value, int code) {

    }

    @Override
    public int emptyLayoutId() {
        return 0;
    }

    @Override
    public int loadingLayoutId() {
        return 0;
    }

    @Override
    public int errorLayoutId() {
        return 0;
    }

    @Override
    public boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    public boolean isBlackIconStatusBar() {
        return false;
    }

    @Override
    public String initTag() {
        return null;
    }


    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public boolean isDelayDate() {
        return false;
    }

    @Override
    public void initDataWhenDelay() {

    }

    @Override
    public Object getPresenter() {
        return null;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public boolean isOpenEventBus() {
        return false;
    }

    @Override
    public boolean isOpenViewState() {
        return false;
    }

    @Override
    public boolean isShowBackButtonInDefaultView() {
        return false;
    }

    @Override
    public void activityFinish() {

    }

    @Override
    public void activityFinish(boolean finishAfterTransition) {

    }

    @Override
    public void loading() {

    }

    @Override
    public void loading(String message) {

    }

    @Override
    public void loading(boolean cancelAble) {

    }

    @Override
    public void loading(String message, boolean cancelAble) {

    }

    @Override
    public void loadingClose() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showContentView() {

    }

    @Override
    public int currentViewState() {
        return 0;
    }

    @Override
    public void intent2Activity(Class clazz) {

    }

    @Override
    public void intent2Activity(Class clazz, int requestCode) {

    }

    @Override
    public void intent2Activity(Class clazz, Bundle bundle) {

    }

    @Override
    public void intent2Activity(Class clazz, Bundle bundle, ActivityOptionsCompat optionsCompat) {

    }

    @Override
    public void intent2Activity(Class clazz, Bundle bundle, int requestCode, ActivityOptionsCompat optionsCompat) {

    }

    @Override
    public void commitFragment(android.support.v4.app.Fragment fragment) {

    }

    @Override
    public void commitFragment(android.support.v4.app.Fragment fragment, String trg) {

    }

    @Override
    public void commitFragment(int layoutId, android.support.v4.app.Fragment fragment) {

    }

    @Override
    public void commitFragment(int layoutId, android.support.v4.app.Fragment fragment, String trg) {

    }

    @Override
    public void commitFragment(android.support.v4.app.Fragment oldFragment, android.support.v4.app.Fragment fragment) {

    }

    @Override
    public void commitFragment(android.support.v4.app.Fragment oldFragment, android.support.v4.app.Fragment fragment, String trg) {

    }

    @Override
    public void commitFragment(android.support.v4.app.Fragment oldFragment, int layoutId, android.support.v4.app.Fragment fragment) {

    }

    @Override
    public void commitFragment(android.support.v4.app.Fragment oldFragment, int layoutId, android.support.v4.app.Fragment fragment, String trg) {

    }

    @Override
    public void commitBackStackFragment(android.support.v4.app.Fragment fragment) {

    }

    @Override
    public void commitBackStackFragment(android.support.v4.app.Fragment fragment, String trg) {

    }

    @Override
    public void commitBackStackFragment(int layoutId, android.support.v4.app.Fragment fragment) {

    }

    @Override
    public void commitBackStackFragment(int layoutId, android.support.v4.app.Fragment fragment, String trg) {

    }


}
