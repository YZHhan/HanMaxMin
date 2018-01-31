package com.han.hanmaxmin.mvp.adapter;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.han.hanmaxmin.common.widget.viewpager.HanViewPager;
import com.han.hanmaxmin.common.widget.viewpager.PagerSlidingTabStrip;
import com.han.hanmaxmin.mvp.HanIIViewPagerActivity;
import com.han.hanmaxmin.mvp.HanIViewPagerABActivity;
import com.han.hanmaxmin.mvp.fragment.hanifragment.HanIViewPagerFragment;


/**
 * @CreateBy Administrator
 * @Date 2018/1/4  21:32
 * @Description tab和ViewPager的适配器
 * 继承HanViewPagerAdapter  实现PagerSlidingTabStrip  的CustomTabProvider
 */
public class HanTabViewPagerAdapter extends HanViewPagerAdapter implements PagerSlidingTabStrip.CustomTabProvider {
   protected String initTag(){
       return getClass().getSimpleName();
   }

   public HanTabViewPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, HanViewPager pager, HanIViewPagerFragment j2WIViewViewpagerFragment){
       super(tag, fragmentManager, tabs, pager, j2WIViewViewpagerFragment);
   }

    public HanTabViewPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, HanViewPager pager, HanIViewPagerABActivity j2WIViewViewpagerABActivity){
        super(tag, fragmentManager, tabs, pager, j2WIViewViewpagerABActivity);
    }


    public HanTabViewPagerAdapter(String tag, FragmentManager fragmentManager, PagerSlidingTabStrip tabs, HanViewPager pager, HanIIViewPagerActivity j2WIViewViewpagerActivity){
        super(tag, fragmentManager, tabs, pager, j2WIViewViewpagerActivity);
    }

    /**
     * PagerSlidingTabStrip  的CustomTabProvider接口方法
     * 得当当前activity的tab的view
     *
     */
    @Override
    public int getCustomTabView() {
        if(viewPagerABActivity != null){
            if(viewPagerABActivity.getTabItemLayout() == 0) throw new IllegalArgumentException("HanTabViewPagerAdapter  必须要有自定义布局！");
            return viewPagerABActivity.getTabItemLayout();
        }

        if(viewPagerActivity != null){
            if(viewPagerActivity.getTabItemLayout() == 0) throw new IllegalArgumentException("HanViewPagerAdapter  必须要有自定义布局！");
            return viewPagerActivity.getTabItemLayout();
        }

        if(viewPagerFragment != null){
            if(viewPagerFragment.getTabItemLayout() == 0) throw new IllegalArgumentException("HanViewpagerFragment  必须要有自定义布局！");
            return viewPagerFragment.getTabItemLayout();
        }

        return 0;
    }

    /**
     * 初始化当前item的tab。
     * @param view
     * @param position
     */
    @Override
    public void initTabsItem(View view, int position) {
        if(viewPagerABActivity != null)viewPagerABActivity.initTab(view, viewPagerData[position]);
        if(viewPagerActivity != null)viewPagerActivity.initTab(view, viewPagerData[position]);
        if(viewPagerFragment != null)viewPagerFragment.initTab(view, viewPagerData[position]);

    }
}
