package com.han.hanmaxmin.mvp.fragment;

import com.han.hanmaxmin.common.widget.ptr.PtrFrameLayout;
import com.han.hanmaxmin.common.widget.ptr.PtrUIHandler;

/**
 * Created by ptxy on 2018/3/20.
 */

public interface HanIPullHeaderViewPagerFragment extends HanIHeaderViewPagerFragment{

    PtrUIHandler getPtrUIHandlerView();

    void onRefresh();

    void startRefreshing();

    void stopRefreshing();

    void openPullRefreshing();

    void closePullRefreshing();

    PtrFrameLayout getPtrFrameLayout();
}
