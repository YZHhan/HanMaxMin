package com.han.hanmaxmin.mvp.fragment.hanifragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.han.hanmaxmin.mvp.fragment.HanIFragment;
import com.han.hanmaxmin.mvp.model.HanModelPager;

/**
 * @CreateBy Administrator
 * @Date 2017/11/23  10:46
 * @Description
 */

public interface HanIViewPagerFragment extends HanIFragment {

    void onPageScrollStateChanged(int state);

    void onPageSelected(View childAt, View oldView, int position, int oldPosition);

    void onPageScrolled(int position, float positionOffet, int positionOffsetPixels);

    void initViewPager();//待完善   void initViewPager(QsModelPager[] modelPagers, int offScreenPageLimit);

    void getModelPager();//待完善   QsModelPager[] getModelPagers();

    void replaceViewPagerItem();//待完善 void replaceViewPageItem(QsModelPager... modelPagers);

    void setIndex(int index, boolean bool);

    void getTabs();//待完善   PagerSlidingTabStrip getTabs();

    void getViewPager();//待完善   PagerSlidingTabStrip getTabs();

    void getViewPagerAdapter();//待完善 QsViewPagerAdapter getViewPagerAdapter();

    int getTabItemLayout();

    void initTab(View view, HanModelPager modelPager);//待完善   void initTab(View view, QsModelPager modelPager);

    Fragment getCurrentFragment();

}
