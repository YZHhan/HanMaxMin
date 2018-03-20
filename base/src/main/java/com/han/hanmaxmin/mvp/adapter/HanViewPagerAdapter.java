package com.han.hanmaxmin.mvp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.widget.viewpager.HanViewPager;
import com.han.hanmaxmin.common.widget.viewpager.PagerSlidingTabStrip;
import com.han.hanmaxmin.common.widget.viewpager.headerpager.base.InnerScrollerContainer;
import com.han.hanmaxmin.common.widget.viewpager.headerpager.base.OuterPagerAdapter;
import com.han.hanmaxmin.common.widget.viewpager.headerpager.base.OuterScroller;
import com.han.hanmaxmin.mvp.HanIIViewPagerActivity;
import com.han.hanmaxmin.mvp.HanIViewPagerABActivity;
import com.han.hanmaxmin.mvp.fragment.HanIFragment;
import com.han.hanmaxmin.mvp.fragment.HanIViewPagerFragment;
import com.han.hanmaxmin.mvp.model.HanModelPager;

/**
 * Created by ptxy on 2018/1/14.
 * Fragment 和 PageAdapter  除了PagerAdapter  （FragmentPagerAdapter和FragmentStatePagerAdapter）
 * FragmentPagerAdapter和FragmentStatePagerAdapter更专注于每个页面是Fragment。前者适用于页面较少的后者适用于页面较多。
 *
 */

public class HanViewPagerAdapter extends PagerAdapter implements OuterPagerAdapter, ViewPager.OnPageChangeListener{
   private static final String TAG = "HanViewPagerAdapter";
   private View oldView = null;
   private int oldPosition = -1;
   private int currentPageIndex = -1; // 当前page索引( 切换之前)
   private int replacePosition = -1;    //替换标识

    private OuterScroller mOuterScroller;
    private FragmentManager mFragmentManager;
    protected String tag;
    protected PagerSlidingTabStrip tabs;
    private HanViewPager pager;  //ViewPager (自定义)
    private ViewGroup container;
    protected HanModelPager[] viewPagerData;//  Pager的Fragment的Model。
    protected HanIViewPagerFragment viewPagerFragment;// protected 可以作为子类使用
    protected HanIViewPagerABActivity viewPagerABActivity;
    protected HanIIViewPagerActivity viewPagerActivity;

    protected String initTag(){
        return getClass().getSimpleName();
    }

    /**
     * 构造函数  区分Fragment和Activity
     */
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
     * 得到pager的索引，
     *
     */
    public void replaceViewPagerDatas(HanModelPager... modelPagers){
        replacePosition = pager.getCurrentItem();//得到当前item的索引。
        for (HanModelPager modelPager : modelPagers){
            int position = modelPager.position;
            container.removeView(viewPagerData[position].fragment.getView());// 移除Fragment的view
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

    public HanModelPager getData(int position){
        return viewPagerData[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return viewPagerData[position].title;
    }

    /**
     * 多少个Fragment
     * @return
     */
    @Override
    public int getCount() {
        return viewPagerData.length;
    }

    /**
     * 销毁条目对象
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        this.container = container;
        if (position < viewPagerData.length && viewPagerData[position].fragment != null) {
            container.removeView(viewPagerData[position].fragment.getView());
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化显示的条目对象
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        this.container = container;
        Fragment fragment = viewPagerData[position].fragment;
        if(!fragment.isAdded()){//如果该Fragment对象被添加到了它的Activity中，那么它返回true，否则返回false。
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);//fragment的切换动画
            fragmentTransaction.add(fragment, fragment.getClass().getSimpleName() + position);
            fragmentTransaction.commitAllowingStateLoss();
            //方法并不立即执行transaction中包含的动作,而是把它加入到UI线程队列中.
            //如果想要立即执行,可以在commit之后立即调用FragmentManager的executePendingTransactions()方法
            mFragmentManager.executePendingTransactions();
            fragment.setHasOptionsMenu(false);// 设置ActionBar不执行
            if(replacePosition != -1){
                if (viewPagerData[replacePosition].fragment instanceof HanIFragment) {
                    ((HanIFragment) viewPagerData[replacePosition].fragment).initDataWhenDelay();
                    ((HanIFragment) viewPagerData[replacePosition].fragment).onActionBar();
                }
                viewPagerData[replacePosition].fragment.onResume();
                replacePosition = -1;
            }
        }
        if (fragment.getView() == null) throw new NullPointerException("fragment has not view...");
        if (fragment.getView().getParent() == null) container.addView(fragment.getView());

        pager.setObjectForPosition(fragment.getView(), position);

        if (mOuterScroller != null && fragment instanceof InnerScrollerContainer) {
            L.i(TAG, "activate header viewpager... current fragment is:" + fragment.getClass().getSimpleName());
            ((InnerScrollerContainer) fragment).setMyOuterScroller(mOuterScroller, position);
        }
        return fragment.getView();
    }

    @Override
    public void setPageOuterScroller(OuterScroller outerScroller) {
        this.mOuterScroller = outerScroller;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (viewPagerFragment != null) viewPagerFragment.onPageScrolled(position, positionOffset, positionOffsetPixels);
        if (viewPagerABActivity != null) viewPagerABActivity.onPageScrolled(position, positionOffset, positionOffsetPixels);
        if (viewPagerActivity != null) viewPagerActivity.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (currentPageIndex == -1) {
            currentPageIndex = position;
            oldView = tabs.tabsContainer.getChildAt(0);
            oldPosition = position;
        }

        if (currentPageIndex < viewPagerData.length) viewPagerData[currentPageIndex].fragment.onPause();

        if (position < viewPagerData.length && viewPagerData[position].fragment.isAdded()) {
            if (viewPagerData[position].fragment instanceof HanIFragment) {
                ((HanIFragment) viewPagerData[position].fragment).initDataWhenDelay(); // 调用延迟加载
                if (pager.getCurrentItem() == position) {
                    ((HanIFragment) viewPagerData[position].fragment).onActionBar();
                }
            }
            viewPagerData[position].fragment.onResume();
        }
        currentPageIndex = position;
        if (viewPagerFragment != null) viewPagerFragment.onPageSelected(tabs.tabsContainer.getChildAt(position), oldView, position, oldPosition);
        if (viewPagerABActivity != null) viewPagerABActivity.onPageSelected(tabs.tabsContainer.getChildAt(position), oldView, position, oldPosition);
        if (viewPagerActivity != null) viewPagerActivity.onPageSelected(tabs.tabsContainer.getChildAt(position), oldView, position, oldPosition);

        oldView = tabs.tabsContainer.getChildAt(position);
        oldPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (viewPagerFragment != null) viewPagerFragment.onPageScrollStateChanged(state);
        if (viewPagerABActivity != null) viewPagerABActivity.onPageScrollStateChanged(state);
        if (viewPagerActivity != null) viewPagerActivity.onPageScrollStateChanged(state);
    }

    @Override
    public void notifyDataSetChanged() {
        currentPageIndex = -1;
        super.notifyDataSetChanged();
    }
}
