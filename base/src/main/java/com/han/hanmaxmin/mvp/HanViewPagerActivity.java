package com.han.hanmaxmin.mvp;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.widget.viewpager.HanViewPager;
import com.han.hanmaxmin.common.widget.viewpager.PagerSlidingTabStrip;
import com.han.hanmaxmin.mvp.adapter.HanTabViewPagerAdapter;
import com.han.hanmaxmin.mvp.adapter.HanViewPagerAdapter;
import com.han.hanmaxmin.mvp.model.HanModelPager;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * @CreateBy Administrator
 * @Date 2018/1/4  21:32
 * @Description 作为ViewPager和Activity的混合
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
        initViewPager(view);//给当前的View绑定对象。ViewPager和PagerSlidingTabStrip
        return view;
    }

    /**
     * 初始化组件对象，ViewPager和PagerSlidingTabStrip。
     * 初始化Tab的属性
     * 初始化ViewPager的属性
     * @param view
     */
    private void initViewPager(View view){
        pager = (HanViewPager) view.findViewById(R.id.han_viewpager);
        tabs = (PagerSlidingTabStrip) view.findViewById(android.R.id.tabs);
        initTabValue(tabs);// 初始化Tab的属性。
        initViewPager(getModelPagers(), 3);// 初始化ViewPager的一些属性，adapter等等。
    }

    /**
     * 得到适配器
     * 设置model
     * 绑定适配器
     * 设置ViewPage的属性。预加载，margin。
     * pager和tab的绑定
     */
    @Override
    public void initViewPager(HanModelPager[] modelPagers, int offScreenPageLimit) {
        if(modelPagers != null){
             adapter = getPagerAdapter(pager, tabs);
             adapter.setModelPagers(modelPagers);
             pager.setAdapter(adapter);
             final int pagerMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());//将其他尺寸转化为px
             pager.setPageMargin(pagerMargin);
             pager.setOffscreenPageLimit(offScreenPageLimit);//预加载页面个数。
             tabs.setViewPager(pager);
        }
    }

    public HanViewPagerAdapter getPagerAdapter(HanViewPager pager, PagerSlidingTabStrip tabs){
        return new HanTabViewPagerAdapter(initTag(), getSupportFragmentManager(), tabs, pager,this);
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

    /**
     * 替换ViewPager的 item
     * @param modelPagers
     */
    @Override
    public void replaceViewPagerItem(HanModelPager... modelPagers) {
        if(adapter != null){
            adapter.replaceViewPagerDatas(modelPagers);
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
        return adapter.getAllData()[pager.getCurrentItem()].fragment;
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
