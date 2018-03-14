package com.han.hanmaxmin.common.widget.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;

import com.han.hanmaxmin.activity.listview.recycler.RecyclerAdapter;
import com.han.hanmaxmin.hantext.aspect.intent.Intent;

import java.util.List;

/**
 * Created by ptxy on 2018/3/8.
 *  ListView/GridView/RecyclerView  的Footer和Header  View 的适配器。
 *  RecyclerView 使用  继承RecyclerView.Adapter
 */

public class HeaderFooterRecyclerAdapter extends RecyclerView.Adapter {

    protected List<View>               headerView;
    protected List<View>               footerView;
    private RecyclerView.Adapter       tagAdapter;

    //定义FooterView类型  和  HeaderView的类型
    private final static int HEADER_VIEW_TYPE = Integer.MAX_VALUE / 123;
    private final static int FOOTER_VIEW_TYPE = Integer.MAX_VALUE / 321;

    public HeaderFooterRecyclerAdapter(RecyclerView.Adapter tagAdapter, List<View> headerView, List<View> footerView) {
            this.tagAdapter = tagAdapter;
            this.headerView = headerView;
            this.footerView = footerView;
    }

    @Override
    public int getItemViewType(int position) {
        int headerNum = headerView.size();
        if(position < headerNum){
            return HEADER_VIEW_TYPE;
        } else if (position >= headerNum){
            int itemCount = tagAdapter.getItemCount();
            int realPosition = position - headerNum;
            if(realPosition < itemCount){
                return tagAdapter.getItemViewType(realPosition);
            }
        }
        return FOOTER_VIEW_TYPE;
    }

    /**
     * 返回ViewHolder
     * 根据ViewType  返回多布局
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case HEADER_VIEW_TYPE:
                return new HeaderOrFooterView(headerView.get(0));
            case FOOTER_VIEW_TYPE:
                return new HeaderOrFooterView(footerView.get(0));
            default:
                return tagAdapter.onCreateViewHolder(parent, viewType);
        }
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerNum = headerView.size();
        if(position <headerNum){
            onHeaderFooterBindViewHolder(holder);
        } else if (position >= headerNum){
            int itemCount = tagAdapter.getItemCount();
            int realPosition = position - headerNum;
            if(realPosition < itemCount){
                tagAdapter.onBindViewHolder(holder, realPosition);
            }
            return;
        }
        onHeaderFooterBindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return tagAdapter.getItemCount() + headerView.size() +footerView.size();
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if(holder instanceof HeaderOrFooterView){
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if(layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                params.setFullSpan(true);
            }
        }
    }

    public void registerDataSetObserver(RecyclerView.AdapterDataObserver mDataObserver){
        if(tagAdapter != null){
            tagAdapter.registerAdapterDataObserver(mDataObserver);
        }
    }

    public void unRegisterDataSetObserver(RecyclerView.AdapterDataObserver mDataObserver){
        if(tagAdapter != null){
            tagAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
    }

    private void onHeaderFooterBindViewHolder(RecyclerView.ViewHolder holder){
        if(holder instanceof HeaderOrFooterView){
            HeaderOrFooterView headerHolder = (HeaderOrFooterView) holder;
            headerHolder.bindData();
        }
    }

    public interface OnRecyclerViewAdapterBindViewHolder{
        void onAdapterBindViewHolder();
    }

    /**
     * headerView 和 footerView    ViewHolder的持有类。
     */
    private class HeaderOrFooterView extends RecyclerView.ViewHolder{
        View view;

        public HeaderOrFooterView(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            this.view = itemView;
        }

        void bindData(){
            if(view != null && view instanceof OnRecyclerViewAdapterBindViewHolder){
                OnRecyclerViewAdapterBindViewHolder view = (OnRecyclerViewAdapterBindViewHolder) this.view;
                view.onAdapterBindViewHolder();
            }
        }
    }



}
