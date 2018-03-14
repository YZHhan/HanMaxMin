package com.han.hanmaxmin.mvp.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.han.hanmaxmin.common.widget.viewpager.HanViewPager;
import com.han.hanmaxmin.common.widget.viewpager.PagerSlidingTabStrip;
import com.han.hanmaxmin.common.widget.viewpager.headerpager.base.OuterPagerAdapter;
import com.han.hanmaxmin.common.widget.viewpager.headerpager.base.OuterScroller;
import com.han.hanmaxmin.mvp.HanIIViewPagerActivity;
import com.han.hanmaxmin.mvp.HanIViewPagerABActivity;
import com.han.hanmaxmin.mvp.fragment.HanIViewPagerFragment;
import com.han.hanmaxmin.mvp.model.HanModelPager;

/**
 * Created by ptxy on 2018/1/14.
 */

public class HanViewPagerAdapter extends PagerAdapter implements OuterPagerAdapter, ViewPager.OnPageChangeListener{
   private static final String TAG = "HanViewPagerAdapter";
   private View oldView = null;
   private int oldPosition = -1;
   private int currentPageIndex = -1; // 当前page索引
   private int replacePosition = -1;    //替换标识

    private OuterScroller mOuterScroller;
    private FragmentManager mFragmentManager;
    protected String tag;
    protected PagerSlidingTabStrip tabs;
    private HanViewPager pager;
    private ViewGroup container;
    protected HanModelPager[] viewPagerData;
    protected HanIViewPagerFragment viewPagerFragment;// protected 可以作为子类使用
    protected HanIViewPagerABActivity viewPagerABActivity;
    protected HanIIViewPagerActivity viewPagerActivity;

    protected String initTag(){
        return getClass().getSimpleName();
    }

    public HanViewPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, HanViewPager pager, HanIViewPagerFragment viewPagerFragment) {
        this.tag = tag;
        this.viewPagerFragment = viewPagerFragment;
        this.mFragmentManager = fragmentManager;
        this.tabs = tabs;
        this.pager = pager;
        this.tabs.setOnPageChangeListener(this);
    }

    public HanViewPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, HanViewPager pager, HanIViewPagerABActivity viewPagerABActivity) {
        this.tag = tag;
        this.viewPagerABActivity = viewPagerABActivity;
        this.mFragmentManager = fragmentManager;
        this.tabs = tabs;
        this.pager = pager;
        this.tabs.setOnPageChangeListener(this);
    }



    public HanViewPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, HanViewPager pager, HanIIViewPagerActivity viewPagerActivity) {
        this.tag = tag;
        this.viewPagerActivity = viewPagerActivity;
        this.mFragmentManager = fragmentManager;
        this.tabs = tabs;
        this.pager = pager;
        this.tabs.setOnPageChangeListener(this);
    }

    /**
     * 设置数据
     */
    public void setModelPagers(HanModelPager[] data){
        this.viewPagerData = data;
    }

    /**
     * 替换
     * HanModelPager...  数组
     *
     */
    public void replaceViewPagerDatas(HanModelPager... modelPagers){
        replacePosition = pager.getCurrentItem();
        for (HanModelPager modelPager : modelPagers){
            int position = modelPager.position;
            container.removeView(viewPagerData[position].fragment.getView());
            FragmentTransaction fragmentTransaction = this.mFragmentManager.beginTransaction();
            fragmentTransaction.detach(viewPagerData[position].fragment).commitAllowingStateLoss();
            viewPagerData[position]= modelPager;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public HanModelPager[] getAllData(){
        return viewPagerData;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public void setPageOuterScroller(OuterScroller outerScroller) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
