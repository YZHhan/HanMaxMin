package com.han.hanmaxmin.mvp.fragment.hanifragment;

/**
 * @CreateBy Administrator
 * @Date 2017/11/23  15:27
 * @Description
 */

public interface HanIPullListFragment<D> extends HanIListFragment<D> {
    void getPtrUIHandlerView();//待完善

    void onRefresh();

    void onLoad();

    void startRefreshing();

    void stopRefreshing();

    void setLoadingState();//待完善

    void openPullRefreshing();

    void closePullRefreshing();

    void openPullLoading();

    void slosePullLoading();

    void getPtrFragment();
}