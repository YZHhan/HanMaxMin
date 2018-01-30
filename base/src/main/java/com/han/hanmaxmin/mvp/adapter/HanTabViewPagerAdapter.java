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
 * Created by ptxy on 2018/1/20.
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

    @Override
    public int getCustomTabView() {
        if(viewPagerABActivity != null){

        }
        return 0;
    }

    @Override
    public void initTabsItem(View view, int position) {

    }
}
