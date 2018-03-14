package com.han.hanmaxmin.mvp.fragment;

import com.han.hanmaxmin.common.widget.listview.LoadingFooter;
import com.han.hanmaxmin.common.widget.ptr.PtrFrameLayout;
import com.han.hanmaxmin.common.widget.ptr.PtrUIHandler;

/**
 * @CreateBy Administrator
 * @Date 2017/11/23  15:27
 * @Description
 */

public interface HanIPullListFragment<D> extends HanIListFragment<D> {
    PtrUIHandler getPtrUIHandlerView();

    void onRefresh();//

    void onLoad();

    void startRefreshing();

    void stopRefreshing();

    void setLoadingState(LoadingFooter.State state);

    void openPullRefreshing();

    void closePullRefreshing();

    void openPullLoading();

    void closePullLoading();

    PtrFrameLayout getPtrFragment();
}
