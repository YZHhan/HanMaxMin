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
    public HanModelPager[] getModelPagers() {
        return new HanModelPager[0];
    }

    /**
     * 这是一个 tab 的
     */
    @Override public int getTabItemLayout() {
        return 0;
    }

    @Override
    public void initTab(View tabItem, HanModelPager modelPager) {

    }
}
