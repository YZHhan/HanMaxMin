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
 * PtrFrameLayout ：设置刷新，只需要三步
 * 1.设置setPtrHandler：主要是为了判断是否可以刷新（考虑到有AbsListView等滑动view的情况）以及设置刷新前的设置：
 * 2.设置PtrUIHandler，通过addPtrUIHandler为PtrFrameLayout设置PtrUIHandler，此设置主要是控制下拉时UI变化，
 * 当然，所引用的第三方库有写好的PtrUIHandler：
 * 3.设置HeaderView，通过setHeaderView，此第三方库也是包含写好的view的；PtrClassicDefaultHeader 就是其中一个了
 *
 *
 *
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
        getRecyclerView().addOnScrollListener(mOnScrollListener);// 添加滑动。监听进行刷新的功能
        return view;
    }

    @Override
    protected void initRecyclerView(LayoutInflater inflater, View view) {
        super.initRecyclerView(inflater, view);
        View footerView = getFooterView();
        if (footerView instanceof LoadingFooter) {
            mLoadingFooter = (LoadingFooter) footerView;
        } else if (footerView != null) {
            mLoadingFooter = (LoadingFooter) footerView.findViewById(R.id.loading_footer);
        }
    }

    @Override
    public PtrUIHandler getPtrUIHandlerView() {
        return new StoreHouseHeader(getContext());
    }

    /**
     *  setPtrHandler：主要是为了判断是否可以刷新（考虑到有AbsListView等滑动view的情况）以及设置刷新前的设置：
     *  设置PtrUIHandler，通过addPtrUIHandler为PtrFrameLayout设置PtrUIHandler，此设置主要是控制下拉时UI变化，当然，所引用的第三方库有写好的PtrUIHandler：
     */
    protected void initPtrFrameLayout(View view) {
        if (view instanceof PtrFrameLayout) {
            mPtrFrameLayout = (PtrFrameLayout) view;
        } else {
            mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.swipe_container);
        }
        if (mPtrFrameLayout == null)
            throw new RuntimeException("PtrFrameLayout is not exit or its id not 'R.id.swipe_container' in current layout！！");
        mPtrFrameLayout.setHeaderView((View) getPtrUIHandlerView());
        mPtrFrameLayout.addPtrUIHandler(getPtrUIHandlerView());//下拉时 UI的变化
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {// 检查是否可以刷新
                /**
                 *检查是否可以刷新，这里使用默认的PtrHandler进行判断
                 */
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                /**
                 * 在刷新前需要准备什么工作
                 */
                L.i("PtrFrameLayout", "id = "+Thread.currentThread());
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
        if(mLoadingFooter != null){//  -------------
            L.i(initTag(), "设置刷新尾部状态");
            mLoadingFooter.setState(state); //----------------
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
            loadingMoreData();// -----------------
        }
    };

    private   void loadingMoreData(){
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
            setLoadingState(LoadingFooter.State.Loading);// --------------
            onLoad();
        }
    }


    //////





























}
