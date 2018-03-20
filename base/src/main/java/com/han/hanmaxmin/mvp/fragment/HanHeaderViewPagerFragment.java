package com.han.hanmaxmin.mvp.fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.widget.viewpager.HanViewPager;
import com.han.hanmaxmin.common.widget.viewpager.PagerSlidingTabStrip;
import com.han.hanmaxmin.common.widget.viewpager.headerpager.HeaderViewPager;
import com.han.hanmaxmin.common.widget.viewpager.headerpager.helper.MagicHeaderUtils;
import com.han.hanmaxmin.mvp.adapter.HanTabViewPagerAdapter;
import com.han.hanmaxmin.mvp.adapter.HanViewPagerAdapter;
import com.han.hanmaxmin.mvp.fragment.HanIHeaderViewPagerFragment;
import com.han.hanmaxmin.mvp.fragment.HanViewPagerFragment;
import com.han.hanmaxmin.mvp.model.HanModelPager;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * Created by ptxy on 2018/3/20.
 */

public abstract class HanHeaderViewPagerFragment <P extends HanPresenter> extends HanViewPagerFragment<P> implements HanIHeaderViewPagerFragment {

    protected HeaderViewPager headerViewPager;

    @Override
    public int layoutId() {
        return R.layout.han_fragment_viewpager;
    }

    @Override
    public ViewGroup createTabView() {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.han_layout_tabs, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MagicHeaderUtils.dp2px(getContext(), 48));
        viewGroup.setLayoutParams(lp);
        return viewGroup;
    }

    @Override
    public void initViewPager(HanModelPager[] modelPagers, int offScreenPageLimit) {
        if (modelPagers != null && modelPagers.length > 0) {
            adapter = createPagerAdapter(pager, tabs);
            adapter.setModelPagers(modelPagers);
            pager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()));
            pager.setOffscreenPageLimit(offScreenPageLimit);
            headerViewPager.setPagerAdapter(adapter);
        }
    }
}
