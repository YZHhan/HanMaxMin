package com.han.hanmaxmin.mvp.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.widget.listview.LoadingFooter;
import com.han.hanmaxmin.common.widget.recyclerview.HeaderFooterRecyclerView;
import com.han.hanmaxmin.mvp.adapter.HanRecyclerAdapterItem;
import com.han.hanmaxmin.mvp.adapter.MyRecyclerViewHolder;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptxy on 2018/3/8.
 */

public abstract class HanRecyclerFragment<P extends HanPresenter, D> extends HanFragment<P> implements HanIRecyclerFragment<D>, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final int TYPE_LIST = 1 << 2;
    public static final int TYPE_GRID = 2 << 2;
    public static final int TYPE_STAGGEREDGRID = 3 << 2;
    private final List<D> mList = new ArrayList<>();

    private HeaderFooterRecyclerView mRecyclerView;
    protected RecyclerView.Adapter mRecyclerViewAdapter;
    private View headerView;
    private View footerView;
    protected StaggeredGridLayoutManager staggeredGridLayoutManager;// 实现交错式网格布局

    @Override
    public int layoutId() {
        return R.layout.han_fragment_recyclerview;
    }

    @Override
    public int getHeaderLayout() {
        return 0;
    }

    @Override
    public int getFooterLayout() {
        return 0;
    }

    @Override
    public int getTopLayout() {
        return 0;
    }

    @Override
    public int getBottomLayout() {
        return 0;
    }

    @Override
    public RecyclerView.Adapter onCreateAdapter() {
        return null;
    }

    @Override
    public HeaderFooterRecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View rootView = super.initView(inflater);
        if (getTopLayout() > 0 || getBottomLayout() < 0) initTopBottomView(rootView, inflater);
        initRecyclerView(inflater, rootView);
        return rootView;
    }

    /**
     * 初始化Recycler
     */
    protected void initRecyclerView(LayoutInflater inflater, View view) {
        if (view instanceof HeaderFooterRecyclerView) {
            mRecyclerView = (HeaderFooterRecyclerView) view;
        } else {
            mRecyclerView = (HeaderFooterRecyclerView) view.findViewById(android.R.id.list);
        }
        if (mRecyclerView == null)
            throw new RuntimeException("HeaderFooterRecyclerView is not exit or its id 'android.R.id.list' in current layout !!");
        if (getHeaderLayout() > 0) {
             headerView = inflater.inflate(getHeaderLayout(), null);
             mRecyclerView.addHeaderView(headerView);
            HanHelper.getInstance().getViewBindHelper().bind(this, headerView);
        }

        if (getFooterLayout() > 0) {// 控制着FootView。
            footerView = inflater.inflate(getFooterLayout(), null);
             mRecyclerView.addFooterView(footerView);
            HanHelper.getInstance().getViewBindHelper().bind(this, footerView);
        }

        mRecyclerViewAdapter = onCreateAdapter();
        if (mRecyclerViewAdapter == null) {
            mRecyclerViewAdapter = new MyRecyclerAdapter(inflater);
        }
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        switch (getRecyclerViewType()) {
            case TYPE_LIST:// listView
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case TYPE_GRID:
                final GridLayoutManager manager = new GridLayoutManager(getContext(), getSpanCount());//一个是上下文对象，另一个是一行显示几列的参数常量，
                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        // 当有footer 或者 header 要做特殊处理时，让他占满一条
                        L.i(initTag(), "getSpanSize  position :" + position);
                        if (getHeaderLayout() > 0 && position == 0) {
                            return getSpanCount();
                        } else if (getHeaderLayout() > 0 && getFooterLayout() > 0 && position == mList.size() + 1) {
                            return getSpanCount();
                        } else if (getHeaderLayout() == 0 && getFooterLayout() > 0 && position == mList.size()) {
                            return getSpanCount();
                        } else {
                            return 1;
                        }
                    }
                });

                mRecyclerView.setLayoutManager(manager);
                break;

            case TYPE_STAGGEREDGRID:
                staggeredGridLayoutManager = new StaggeredGridLayoutManager(getSpanCount(), StaggeredGridLayoutManager.VERTICAL);
                /*  顶部不留白  */
                staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
                mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                break;
        }
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mRecyclerViewAdapter;
    }

    public View getHeaderView(){
        return headerView;
    }

    public View getFooterView(){
        return footerView;
    }

    @Override
    public void setData(List<D> list) {
        setData(list, true);
    }

    @Override
    public void setData(List<D> list, boolean showEmptyView) {
        synchronized (mList) {
            mList.clear();
            if (list != null && !list.isEmpty()) mList.addAll(list);
            updateAdapter(showEmptyView);
        }
    }

    @Override
    public void addData(D d) {
        if (d != null) {
            synchronized (mList) {
                mList.add(d);
                updateAdapter(true);
            }
        }
    }

    @Override
    public void addData(List<D> list) {
        if (list != null && !list.isEmpty()) {
            synchronized (list) {
                mList.addAll(list);
                updateAdapter(true);
            }
        }
    }

    @Override
    @ThreadPoint(ThreadType.MAIN)
    public void addData(List<D> list, int position) {
        if (list != null && !list.isEmpty() && position >= 0) {
            synchronized (mList) {
                position = (position < mList.size()) ? position : mList.size();
                if (mRecyclerViewAdapter != null)
                    mRecyclerViewAdapter.notifyItemRangeChanged(position, list.size());
                mList.addAll(position, list);
                updateAdapter(true);
            }
        }
    }

    @Override
    public void delete(D d) {
        if (d == null) return;
        synchronized (mList) {
            mList.remove(d);
            updateAdapter(true);
        }
    }

    @Override
    @ThreadPoint(ThreadType.MAIN)
    public void delete(int position) {
        synchronized (mList) {
            if (position >= 0 && position < mList.size()) {
                if (mRecyclerViewAdapter != null) mRecyclerViewAdapter.notifyItemRemoved(position);
                mList.remove(position);
                updateAdapter(true);
            }
        }
    }

    @Override
    public void deleteAll() {
        synchronized (mList) {
            mList.clear();
            updateAdapter(true);
        }
    }

    @Override
    public D getData(int position) {
        if (position >= 0 && position < mList.size()) {
            return mList.get(position);
        }
        return null;
    }

    @Override
    public final List<D> getData() {
        return mList;
    }

    @Override
    @ThreadPoint(ThreadType.MAIN)
    public void updateAdapter(boolean showEmptyView) {
        if (mRecyclerViewAdapter != null) {
            mRecyclerViewAdapter.notifyDataSetChanged();
            if (mViewAnimator != null) {
                if (mList.isEmpty() && showEmptyView) {
                    showEmptyView();
                } else {
                    showContentView();
                }
            }
        }
    }

    protected void initTopBottomView(View    rootView, LayoutInflater inflater) {
        if (rootView instanceof LinearLayout) {
            if (getTopLayout() > 0) {
                ((LinearLayout) rootView).addView(inflater.inflate(getTopLayout(), null), 0);
            }

            if (getBottomLayout() > 0) {
                int childCount = ((LinearLayout) rootView).getChildCount();
                ((LinearLayout) rootView).addView(inflater.inflate(getBottomLayout(), null), childCount);
            }
        } else {
            L.e(initTag(), "rootViewLayout() root view must be LinearLayout when getTopLayout() or getBottomLayout() is overwrite, but now is " + rootView.getClass().getSimpleName());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    /**
     * 封装适配器在Fragment中，作为内部类。我们用的HanRecyclerVIewAdapterITem并不是真正的适配器。
     */
    // ----------------------------------适配器-----------------------------------
    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder<D>> {
        private LayoutInflater mInflater;

        public MyRecyclerAdapter(LayoutInflater inflater) {
            this.mInflater = inflater;
        }

        @Override
        public MyRecyclerViewHolder<D> onCreateViewHolder(ViewGroup parent, int viewType) {
            HanRecyclerAdapterItem recyclerAdapterItem = getRecyclerAdapterItem(mInflater, parent, viewType);
            MyRecyclerViewHolder<D> holder = new MyRecyclerViewHolder<D>(recyclerAdapterItem);
            holder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HanRecyclerFragment.this.onItemClick(parent, view, position, id);
                }
            });

            holder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    return HanRecyclerFragment.this.onItemLongClick(parent, view, position, id);
                }
            });

            return holder;
        }

        /**
         * 绑定数据
         */
        @Override
        public void onBindViewHolder(MyRecyclerViewHolder<D> holder, int position) {
            L.i("Recycler", "position : "  + position);
            holder.onBindData(mList.get(position),position, mList.size());
        }

        @Override
        public int getItemViewType(int position) {
            return HanRecyclerFragment.this.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    protected int getSpanCount() {
        return 2;
    }

    protected int getRecyclerViewType() {
        return TYPE_LIST;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
