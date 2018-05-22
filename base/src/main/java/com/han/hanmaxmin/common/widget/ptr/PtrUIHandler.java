package com.han.hanmaxmin.common.widget.ptr;

import com.han.hanmaxmin.common.widget.ptr.indicator.PtrIndicator;

/**
 * Created by ptxy on 2018/3/11.
 *下拉刷新的UI接口
 * 准备下拉，下拉中，下拉完成，重置，下拉过程位置变化
 *
 */

public interface PtrUIHandler {

    /**
     * 当内容视图到达顶部并刷新完成后，视图将被重置。
     * Content 重新回到顶部， Header 消失，整个下拉刷新过程完全结束以后，重置 View 。
     *  When the content view has reached top and refresh has been completed, view will be reset.
     * @param frame
     */
    void onUIReset(PtrFrameLayout frame);

    /**
     *  prepare for loading
     *  准备刷新，Header 将要出现时调用。
     * @param frame
     */
    void onUIRefreshPrepare(PtrFrameLayout frame);

    /**
     * 开始刷新，Header 进入刷新状态之前调用。
     * preform  refresh UI
     * @param frame
     */
    void onUIRefreshBegin(PtrFrameLayout frame);

    /**
     * perform UI after refresh
     * 刷新结束，Header 开始向上移动之前调用。
     * @param frame
     */
    void onUIRefreshComplete(PtrFrameLayout frame);

    /**
     * 下拉过程中位置变化回调。
     */
    void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator indicator);



























}
