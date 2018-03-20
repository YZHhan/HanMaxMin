package com.han.hanmaxmin.mvp.fragment;

import android.view.ViewGroup;

/**
 * Created by ptxy on 2018/3/17.
 */

public interface HanIHeaderViewPagerFragment extends HanIViewPagerFragment {
    int getHeaderLayout();

    ViewGroup createTabView();
}
