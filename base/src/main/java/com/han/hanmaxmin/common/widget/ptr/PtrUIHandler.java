package com.han.hanmaxmin.common.widget.ptr;

import com.han.hanmaxmin.common.widget.ptr.indicator.PtrIndicator;

/**
 * Created by ptxy on 2018/3/11.
 *
 */

public interface PtrUIHandler {

    /**
     * 当内容视图到达顶部并刷新完成后，视图将被重置。
     *  When the content view has reached top and refresh has been completed, view will be reset.
     * @param frame
     */
    void onUIReset(PtrFrameLayout frame);

    /**
     *  prepare for loading
     * @param frame
     */
    void onUIRefreshPrepare(PtrFrameLayout frame);

    /**
     * preform  refresh UI
     * @param frame
     */
    void onUIRefreshBegin(PtrFrameLayout frame);

    /**
     * perform UI after refresh
     * @param frame
     */
    void onUIRefreshComplete(PtrFrameLayout frame);

    void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator indicator);



























}
