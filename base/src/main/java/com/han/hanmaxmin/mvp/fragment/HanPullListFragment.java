package com.han.hanmaxmin.mvp.fragment;

import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.exception.HanException;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.widget.listview.LoadingFooter;
import com.han.hanmaxmin.common.widget.ptr.PtrDefaultHandler;
import com.han.hanmaxmin.common.widget.ptr.PtrFrameLayout;
import com.han.hanmaxmin.common.widget.ptr.PtrHandler;
import com.han.hanmaxmin.common.widget.ptr.PtrUIHandler;
import com.han.hanmaxmin.common.widget.ptr.header.StoreHouseHeader;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import java.util.List;

import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TemplatesHandler;

/**
 * @CreateBy Administrator
 * @Date 2017/11/23  15:37
 * @Description 带刷新的listview
 * HanPullListFragment 继承与HanListFragment 带上刷新和加载的功能，基于PtrFrameLayout 一系列封装
 */

public abstract class HanPullListFragment<T extends HanPresenter, D> extends HanListFragment<T, D> implements HanIPullListFragment<D> {

    private PtrFrameLayout mPtrFrameLayout;
    protected LoadingFooter mLoadingFooter;
    private boolean canLoadingMore = true;

    /**
     * The fragments of XML layout.
     * Can manually layout file, also can use the default file
     * listView  自定义PtrFrameLayout
     */
    @Override
    public int layoutId() {
        return (!isOpenViewState() && (getTopLayout() > 0 || getBottomLayout() > 0)) ? R.layout.han_fragment_pull_listview_with_top_bottom : R.layout.han_fragment_pull_listview;
    }


    // 修改
    @Override
    public int getFooterLayout() {
        return R.layout.han_loading_footer;
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View view = super.initView(inflater);
        initPtrFrameLayout(view);
        return view;
    }

//    /**
//     * StoreHouse  储存室
//     * @return
//     */
//    @Override
//    public PtrUIHandler getPtrUIHandlerView() {
//        return new StoreHouseHeader(getContext());
//    }

    @Override
    protected void initListView(LayoutInflater inflater, View view) {
        super.initListView(inflater, view);
        View footerView = getFooterView();
        if (footerView instanceof LoadingFooter) {
            mLoadingFooter = (LoadingFooter) footerView;
        } else if (footerView != null) {
            mLoadingFooter = (LoadingFooter) footerView.findViewById(R.id.loading_footer);
        }
    }

    private void initPtrFrameLayout(View view) {
        if (view instanceof PtrFrameLayout) {
            mPtrFrameLayout = (PtrFrameLayout) view;
        } else {
            mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.swipe_container);
        }

        if (mPtrFrameLayout == null)
            throw new RuntimeException("PreFrameLayout  is not exit or its id not 'R.id.swipe_container' in current layout!!");
        mPtrFrameLayout.setHeaderView((View) getPtrUIHandlerView());
        mPtrFrameLayout.addPtrUIHandler(getPtrUIHandlerView());
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                onRefresh();
            }
        });
    }

    /**
     * 后去下拉刷新控件
     */
    @Override
    public PtrFrameLayout getPtrFragment() {
        return mPtrFrameLayout;
    }

    @Override
    @ThreadPoint(ThreadType.MAIN)
    public void startRefreshing() {
        mPtrFrameLayout.autoRefresh();
    }

    @Override
    @ThreadPoint(ThreadType.MAIN)
    public void stopRefreshing() {
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    @ThreadPoint(ThreadType.MAIN)
    public void setLoadingState(LoadingFooter.State state) {
        if (mLoadingFooter != null) {
            L.i(initTag(), "设置刷新尾部状态： " + state);
            mLoadingFooter.setState(state);
        }
    }

    @Override
    @ThreadPoint(ThreadType.MAIN)
    public void openPullRefreshing() {
        mPtrFrameLayout.setEnabled(true);
    }

    @Override
    @ThreadPoint(ThreadType.MAIN)
    public void closePullRefreshing() {
        mPtrFrameLayout.setEnabled(false);
    }

    @Override
    public void openPullLoading() {
        canLoadingMore = true;
    }

    @Override
    public void closePullLoading() {
        canLoadingMore = false;
    }

    @Override
    public void setData(List<D> list) {
        setData(list, true);
    }

    @Override
    @ThreadPoint(ThreadType.MAIN)
    public void setData(List<D> list, boolean showEmptyView) {
        mPtrFrameLayout.refreshComplete();
        super.setData(list, showEmptyView);//因为继承HanListFragment
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        super.onScrollStateChanged(view, scrollState);
        if (!canChildScrollDown() && scrollState == SCROLL_STATE_IDLE) {
            loadingMoreData();
        }
    }

    /**
     * ListView 是否可以滑动到底部。
     *
     * @return
     */
    protected boolean canChildScrollDown() {
        return ViewCompat.canScrollVertically(getListView(), 1);
    }

    private void loadingMoreData() {
        if (mLoadingFooter != null) {
            LoadingFooter.State state = mLoadingFooter.getState();
            if (!canLoadingMore) {
                return;
            } else if (state == LoadingFooter.State.Loading) {
                L.i(initTag(), "Under loading.........");
                return;
            } else if (state == LoadingFooter.State.TheEnd) {
                L.i(initTag(), "no more data.........");
                return;
            }
            setLoadingState(LoadingFooter.State.Loading);
            onLoad();
        }
    }


}
