package com.han.hanmaxmin.mvp.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.widget.ptr.PtrFrameLayout;
import com.han.hanmaxmin.common.widget.ptr.PtrHandler;
import com.han.hanmaxmin.common.widget.viewpager.headerpager.HeaderViewPager;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * Created by ptxy on 2018/3/17.
 */

public abstract class HanPullHeaderViewPagerFragment <P extends HanPresenter> extends HanHeaderViewPagerFragment implements HanIPullHeaderViewPagerFragment {

    private PtrFrameLayout mPtrFrameLayout;

    @Override public int layoutId() {
        return R.layout.han_fragment_pull_header_viewpager;
    }

    @Override
    public void startRefreshing() {
        mPtrFrameLayout.autoRefresh();
    }

    @Override
    public void stopRefreshing() {
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void openPullRefreshing() {
        mPtrFrameLayout.setEnabled(true);
    }

    @Override
    public void closePullRefreshing() {
        mPtrFrameLayout.setEnabled(false);
    }

    @Override
    public PtrFrameLayout getPtrFrameLayout() {
        return mPtrFrameLayout;
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View view = super.initView(inflater);
        initPtrFrameLayout(view);

        return super.initView(inflater);
    }

    protected  void initPtrFrameLayout(View view){
        if (view instanceof PtrFrameLayout) {
            mPtrFrameLayout = (PtrFrameLayout) view;
        } else {
            mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.swipe_container);
        }
        if (mPtrFrameLayout == null) throw new RuntimeException("PtrFrameLayout is not exit or its id not 'R.id.swipe_container' in current layout!!");
        mPtrFrameLayout.setHeaderView((View) getPtrUIHandlerView());
        mPtrFrameLayout.addPtrUIHandler(getPtrUIHandlerView());
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return !canChildScrollUp((HeaderViewPager) content);
            }

            @Override public void onRefreshBegin(PtrFrameLayout frame) {
                onRefresh();
            }
        });
    }

    protected  boolean canChildScrollUp(HeaderViewPager view){
        return view != null && view.getCurrentInnerScroller() != null && view.getCurrentInnerScroller().get() != null && view.getCurrentInnerScroller().get().canScrollVertically(-1);

    }
}

