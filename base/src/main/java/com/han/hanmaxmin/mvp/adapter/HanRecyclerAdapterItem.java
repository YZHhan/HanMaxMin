package com.han.hanmaxmin.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.han.hanmaxmin.common.utils.HanHelper;

/**
 * Created by ptxy on 2018/3/8.
 * RecyclerView 的Item  封装类
 */

public abstract class HanRecyclerAdapterItem<T> {

    public View mItemVIew;
    protected Context mParentContext;

    public HanRecyclerAdapterItem(LayoutInflater inflater, ViewGroup parent) {
        mItemVIew = inflater.inflate(itemViewLayoutId(), parent, false);
        HanHelper.getInstance().getViewBindHelper().bind(this, mItemVIew);
        mParentContext = parent.getContext();
    }

    protected String initTag() {
        return getClass().getSimpleName();
    }

    protected abstract int itemViewLayoutId();

    protected abstract void onBindItemData(T data, int position, int totalCount);

    public Context getContext(){
        return mParentContext;
    }

   View getItemVIew(){
        return mItemVIew;
   }
}
