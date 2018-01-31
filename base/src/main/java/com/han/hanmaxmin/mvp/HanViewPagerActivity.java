package com.han.hanmaxmin.mvp;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.widget.viewpager.HanViewPager;
import com.han.hanmaxmin.common.widget.viewpager.PagerSlidingTabStrip;
import com.han.hanmaxmin.mvp.adapter.HanViewPagerAdapter;
import com.han.hanmaxmin.mvp.model.HanModelPager;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * @CreateBy Administrator
 * @Date 2018/1/4  21:32
 * @Description
 */

public abstract class HanViewPagerActivity<P extends HanPresenter> extends HanActivity<P> implements HanIIViewPagerActivity {

    private HanViewPagerAdapter adapter;    //ViewPager的adapter
    private HanViewPager pager;             //Viewpager
    private PagerSlidingTabStrip tabs;      //单行标题   TAB

    @Override
    public int layoutId() {
        return R.layout.han_activity_viewpager;
    }

    @Override
    protected View initView() {
        View view = super.initView();//spter可以调用父类的方法
        initViewPager(view);
        return view;
    }

    private void initViewPager(View view){
        pager = (HanViewPager) view.findViewById(R.id.han_viewpager);
        tabs = (PagerSlidingTabStrip) view.findViewById(android.R.id.tabs);
        initTabValue(tabs);
        initViewPager(getModelPagers(), 3);
    }

    public final void initTabValue(PagerSlidingTabStrip tabs){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        tabs.setShouldExpand(getTabShouldExpand());//设置tab是自动填充屏幕的
        tabs.setDividerColor(getDeviderColor());//设置tab分割线的颜色
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getTabUnderlineHeight(), dm));//设置tab底部线的高度
        tabs.setUnderlineColor(getUnderlineColor());//设置tab底部线的颜色
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getTabIndicatorSize(), dm));//设置tab Indicator的高度
        tabs.setIndicatorColor(getTabIndicatorColor());
        tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, getTabTitleSize(), dm));//设置title的size
        tabs.setSelectedTextColor(getTabSelectedTitleColor());//设置title选择的颜色
        tabs.setTextColor(getTabsTitleColor());//设置tab的title的颜色
        tabs.setTabBackground(getTabOnClickTitleColor());//设置
        tabs.setBackgroundResource(getTabBackgroundResource());
        tabs.setTabWidth(getTabWidth());
        tabs.setTabMarginsLeftRight(getTabMargin());
        tabs.setTabPaddingLeftRight(getTabPaddingLeftRight());
        tabs.setIndicatorMargin(getTabIndicatorMargin());
        tabs.setIsCurrentItemAnimation(getTabCurrentItemAnimation());



    }

    @Override
    public void onPageScrolled(int position, float positonOffset, int postionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageSelected(View childAt, View oldView, int position, int oldPosition) {

    }

    @Override
    public void replaceViewPagerItem(HanModelPager... modelPagers) {
        if(adapter != null){
//            adapter.re//
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setIndex(int index, boolean bool) {
        int childCount = pager.getAdapter().getCount();
        if(0 < index && index < childCount){
            pager.setCurrentItem(index, bool);
        }
    }

    @Override
    public PagerSlidingTabStrip getTabs() {
        return tabs;
    }

    @Override
    public HanViewPager getViewPager() {
        return pager;
    }

    @Override
    public HanViewPagerAdapter getViewPagerAdapter() {
        return adapter;
    }

    @Override
    public Fragment getCurrentFragment() {
//        return adapter.get;
        return null;
    }



    protected boolean getTabCurrentItemAnimation() {
        return false;
    }

    protected int getTabIndicatorMargin() {
        return 0;
    }

    protected int getTabPaddingLeftRight() {
        return 20;
    }

    protected int getTabMargin() {
        return 0;
    }

    protected int getTabWidth() {
        return 0;
    }

    protected int getTabBackgroundResource() {
        return android.R.color.transparent;
    }

    protected int getTabOnClickTitleColor() {
        return getResources().getColor(R.color.colorAccent);
    }

    protected int getTabsTitleColor() {
        return 0;
    }

    protected int getTabSelectedTitleColor() {
        return 0;
    }

    protected int getTabIndicatorColor() {
        return 0;
    }

    protected float getTabTitleSize() {
        return 12;
    }

    protected float getTabIndicatorSize() {
        return 2;
    }

    protected int getUnderlineColor() {
        return Color.TRANSPARENT;
    }

    protected float getTabUnderlineHeight() {
        return 1;
    }

    protected int getDeviderColor() {
        return Color.TRANSPARENT;
    }

    protected  boolean getTabShouldExpand(){
        return true;
    }


}
