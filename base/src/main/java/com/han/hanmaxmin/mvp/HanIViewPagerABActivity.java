package com.han.hanmaxmin.mvp;

import android.support.v4.app.Fragment;
import android.view.View;

import com.han.hanmaxmin.common.widget.viewpager.HanViewPager;
import com.han.hanmaxmin.common.widget.viewpager.PagerSlidingTabStrip;
import com.han.hanmaxmin.mvp.adapter.HanViewPagerAdapter;
import com.han.hanmaxmin.mvp.model.HanModelPager;

/**
 * @CreateBy Administrator
 * @Date 2018/1/4  21:29
 * @Description  Activity  和 ViewPager的结合，此接口定义的是ViewPager的方法
 */

public interface HanIViewPagerABActivity extends HanIABActivity{
       void onPageScrollStaeChanged(int state);

       void onPageSelected(View childAt, View oldView, int position, int oldPosition);

       void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

       void initViewPager(HanModelPager[] modelPagers, int offScreenPageLimit);

       HanModelPager[] getModelPagers();

       void replaceViewPagerItem(HanModelPager... modelPagers);

       void setIndex(int index, boolean bool);

       PagerSlidingTabStrip getTabs();

       HanViewPager getViewPager();

       HanViewPagerAdapter getViewPagerAdapter();

       int getTabItemLayout();

       void initTab(View view, HanModelPager modelPager);

       Fragment getCurrentFragemnt();

}
