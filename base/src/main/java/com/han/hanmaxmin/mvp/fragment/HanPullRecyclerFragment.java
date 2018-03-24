package com.han.hanmaxmin.mvp.fragment;

import android.media.audiofx.AudioEffect;
import android.sax.EndTextElementListener;
import android.view.LayoutInflater;
import android.view.View;

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
import com.han.hanmaxmin.common.widget.recyclerview.EndlessRecyclerOnScrollListener;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import java.util.List;

import javax.xml.transform.sax.TemplatesHandler;

/**
 * Created by ptxy on 2018/3/15.
 */

public abstract class HanPullRecyclerFragment<P extends HanPresenter, D> extends HanRecyclerFragment<P, D> implements HanIPullRecyclerFragment<D> {

    private PtrFrameLayout mPtrFrameLayout;
    protected LoadingFooter mLoadingFooter;
    private boolean canLoadingMore = true;

    @Override
    public int getFooterLayout() {
        return R.layout.han_loading_footer;
    }

    @Override
    public int layoutId() {
        return (!isOpenViewState() && (getTopLayout() > 0 || getBottomLayout() > 0)) ? R.layout.han_fragment_pull_recyclerview_with_top_bottom : R.layout.han_fragment_pull_recyclerview_with_top_bottom;
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View view = super.initView(inflater);
        initPtrFrameLayout(view);
        return super.initView(inflater);
    }

    @Override
    public PtrUIHandler getPtrUIHandlerView() {
        return new StoreHouseHeader(getContext());
    }

    protected void initPtrFrameLayout(View view) {
        if (view instanceof PtrFrameLayout) {
            mPtrFrameLayout = (PtrFrameLayout) view;
        } else {
            mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.swipe_container);
        }
        if (mPtrFrameLayout == null)
            throw new RuntimeException("PtrframeLayout is not exit or its id not 'R.id.swipe_container' in current layout！！");
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

   @Override  @ThreadPoint(ThreadType.MAIN)
    public void startRefreshing() {
        mPtrFrameLayout.autoRefresh();
    }

   @Override  @ThreadPoint(ThreadType.MAIN)
    public void stopRefreshing() {
        mPtrFrameLayout.refreshComplete();
    }

   @Override  @ThreadPoint(ThreadType.MAIN)
    public void setLoadingState(LoadingFooter.State state) {
        if(mLoadingFooter != null){
            L.i(initTag(), "设置刷新尾部状态");
            mLoadingFooter.setState(state);
        }
    }

    @Override @ThreadPoint(ThreadType.MAIN)
    public void openPullRefreshing() {
        mPtrFrameLayout.setEnabled(true);
    }

    @Override @ThreadPoint(ThreadType.MAIN)
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
    public PtrFrameLayout getPtrFrameLayout() {
        return mPtrFrameLayout;
    }

    @Override
    public void setData(List<D> list) {
       setData(list, true);
    }

    @Override
    public void setData(List<D> list, boolean showEmptyView) {
        mPtrFrameLayout.refreshComplete();//
        super.setData(list, showEmptyView);
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            loadMoreData();
        }
    };

    private   void loadMoreData(){
        if(mLoadingFooter != null){
            LoadingFooter.State state = mLoadingFooter.getState();
            if(!canLoadingMore){
                return;
            } else if (state == LoadingFooter.State.Loading){
                L.i(initTag(), "Under loading...................");
                return;
            } else if (state == LoadingFooter.State.TheEnd){
                L.i(initTag(), "no more data......................");
                return;
            }
            setLoadingState(LoadingFooter.State.Loading);
            onLoad();
        }
    }


    //////





























}
