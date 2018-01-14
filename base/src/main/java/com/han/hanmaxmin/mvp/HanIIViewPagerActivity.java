package com.han.hanmaxmin.mvp;

import android.app.Fragment;
import android.view.View;

import com.han.hanmaxmin.common.widget.viewpager.HanViewPager;
import com.han.hanmaxmin.common.widget.viewpager.PagerSlidingTabStrip;
import com.han.hanmaxmin.mvp.model.HanModelPager;

/**
 * @CreateBy Administrator
 * @Date 2018/1/4  21:27
 * @Description  目前的理解
 *          ViewPager的Activity
 */

public interface HanIIViewPagerActivity extends HanIActivity{
   /**
    * ViewPager页滑动的状态
    */
   void onPageScrollStateChanged(int state);

   /**
    * ViewPager页滑动的监听
    */
   void onPageSelected(View childAt, View oldView, int position, int oldPosition);

   /**
    *ViewPager页的滑动
    */
   void onPageScrolled(int position, float positonOffset, int postionOffsetPixels);

   /**
    * 初始化ViewPAger
    */
   void initViewPager(HanModelPager [] modelPagers, int offScreenPageLimit);

   /**
    * 得到当前View Pager所有页的模型
    */
   HanModelPager[] getModelPagers();

   /**
    * 替换ViewPager的Item
    */
   void replaceViewPagerItem(HanModelPager... modelPagers);

   /**
    * 设置索引
    */
   void setIndex(int index, boolean bool);

   PagerSlidingTabStrip getTabs();

   HanViewPager getViewPager();

   int getTabItemLayout();

   void initTab(View view, HanModelPager modelPager);

   android.support.v4.app.Fragment getCurrentFragment();












}
