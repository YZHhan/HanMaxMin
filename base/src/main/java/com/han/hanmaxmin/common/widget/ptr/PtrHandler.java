package com.han.hanmaxmin.common.widget.ptr;

import android.view.View;

/**
 * Created by ptxy on 2018/3/12.
 * 下拉刷新的功能接口
 * 刷新功能回调以及判断是否可以下拉刷新。
 */

public interface PtrHandler {

    /**
     * Check can do refresh or not. For example the content is empty or the first child is in view
     * 检查可以刷新或不刷新。例如，内容是空的，或者是第一个孩子在视图中。
     *
     * {@link PtrDefaultHandler {@link #checkCanDoRefresh(PtrFrameLayout, View, View)}}
     */
    boolean checkCanDoRefresh(final PtrFrameLayout frame, final View content, final View header);

    /**
     * When refresh begin
     */
    void onRefreshBegin(final PtrFrameLayout frame);
}
