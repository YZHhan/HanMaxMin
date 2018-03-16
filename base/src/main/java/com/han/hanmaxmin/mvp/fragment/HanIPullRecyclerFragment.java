package com.han.hanmaxmin.mvp.fragment;

import com.han.hanmaxmin.common.widget.listview.LoadingFooter;
import com.han.hanmaxmin.common.widget.ptr.PtrFrameLayout;
import com.han.hanmaxmin.common.widget.ptr.PtrUIHandler;

/**
 * Created by ptxy on 2018/3/15.
 */

public interface HanIPullRecyclerFragment <D> extends HanIRecyclerFragment<D> {

    PtrUIHandler getPtrUIHandlerView();

    void onRefresh();

    void onLoad();

    void startRefreshing();

    void stopRefreshing();

    void setLoadingState(LoadingFooter.State state);

    void openPullRefreshing();

    void closePullRefreshing();

    void openPullLoading();

    void closePullLoading();

    PtrFrameLayout getPtrFrameLayout();

}
