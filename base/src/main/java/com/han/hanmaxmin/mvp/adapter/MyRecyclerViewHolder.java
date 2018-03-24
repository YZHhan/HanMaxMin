package com.han.hanmaxmin.mvp.adapter;

import android.animation.ValueAnimator;
import android.net.MailTo;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ViewAnimator;

/**
 * Created by ptxy on 2018/3/8.
 * RecyclerView  ViewHolder
 *
 */

public class MyRecyclerViewHolder <T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    private final HanRecyclerAdapterItem<T>     mAdapterItem;
    private int mPosition;
    private int mTotalCount;
    private AdapterView.OnItemClickListener mClickListener;
    private AdapterView.OnItemLongClickListener mLongClickListener;


    public MyRecyclerViewHolder(HanRecyclerAdapterItem<T> adapterItem) {
        super(adapterItem.getItemVIew());
        this.mAdapterItem = adapterItem;
        adapterItem.getItemVIew().setOnClickListener(this);
        adapterItem.getItemVIew().setOnLongClickListener(this);
    }

    /**
     *
     * @param t 对应索引item的数据   单条数据
     * @param position current index
     * @param totalCount item total
     */
    public  void onBindData(T t, int position, int totalCount){
        this.mPosition = position;
        this.mTotalCount = totalCount;
        mAdapterItem.onBindItemData(t, position, totalCount);
    }

    @Override
    public void onClick(View v) {
        if(mClickListener != null)mClickListener.onItemClick(null, v, mPosition, -1);
    }

    @Override
    public boolean onLongClick(View v) {
        return mClickListener != null && mLongClickListener.onItemLongClick(null, v, mPosition, -1);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.mLongClickListener = onItemLongClickListener;
    }

}
