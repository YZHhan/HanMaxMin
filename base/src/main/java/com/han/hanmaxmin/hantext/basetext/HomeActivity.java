package com.han.hanmaxmin.hantext.basetext;

import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.View;

import com.han.hanmaxmin.mvp.HanViewPagerActivity;
import com.han.hanmaxmin.mvp.model.HanModelPager;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * Created by ptxy on 2018/1/31.
 */

public class HomeActivity extends HanViewPagerActivity<HanPresenter>  {
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public boolean isShowBackButtonInDefaultView() {
        return false;
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
    public void commitFragment(Fragment fragment) {

    }

    @Override
    public void commitFragment(Fragment fragment, String trg) {

    }

    @Override
    public void commitFragment(int layoutId, Fragment fragment) {

    }

    @Override
    public void commitFragment(int layoutId, Fragment fragment, String trg) {

    }

    @Override
    public void commitFragment(Fragment oldFragment, Fragment fragment) {

    }

    @Override
    public void commitFragment(Fragment oldFragment, Fragment fragment, String trg) {

    }

    @Override
    public void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment) {

    }

    @Override
    public void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment, String trg) {

    }

    @Override
    public void commitBackStackFragment(Fragment fragment) {

    }

    @Override
    public void commitBackStackFragment(Fragment fragment, String trg) {

    }

    @Override
    public void commitBackStackFragment(int layoutId, Fragment fragment) {

    }

    @Override
    public void commitBackStackFragment(int layoutId, Fragment fragment, String trg) {

    }

    @Override
    public HanModelPager[] getModelPagers() {
        return new HanModelPager[0];
    }

    @Override
    public int getTabItemLayout() {
        return 0;
    }

    @Override
    public void initTab(View view, HanModelPager modelPager) {

    }
}
