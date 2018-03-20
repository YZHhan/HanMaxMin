package com.han.hanmaxmin.mvp.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.han.hanmaxmin.common.widget.viewpager.HanViewPager;
import com.han.hanmaxmin.common.widget.viewpager.PagerSlidingTabStrip;
import com.han.hanmaxmin.mvp.adapter.HanViewPagerAdapter;
import com.han.hanmaxmin.mvp.fragment.HanIFragment;
import com.han.hanmaxmin.mvp.model.HanModelPager;

/**
 * @CreateBy Administrator
 * @Date 2017/11/23  10:46
 * @Description
 */

public interface HanIViewPagerFragment extends HanIFragment {

    void onPageScrollStateChanged(int state);

    void onPageSelected(View currentTabItem, View oldTabItem, int position, int oldPosition);

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void initViewPager(HanModelPager [] modelPagers, int offScreenPageLimit);

    HanModelPager [] getModelPager();

    void replaceViewPageItem(HanModelPager... modelPagers );

    void setIndex(int index, boolean bool);

    PagerSlidingTabStrip getTabs();

    HanViewPager getViewPager();

    HanViewPagerAdapter getViewPagerAdapter();

    int getTabItemLayout();

    void initTab(View view, HanModelPager modelPager);

    Fragment getCurrentFragment();

}
