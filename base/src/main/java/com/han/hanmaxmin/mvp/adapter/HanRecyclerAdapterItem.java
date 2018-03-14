package com.han.hanmaxmin.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * Created by ptxy on 2018/3/8.
 * RecyclerView 的Item  封装类
 */

public abstract class HanRecyclerAdapterItem<T> {

    public View mItemVIew;
    private MyViewHolder myViewHolder;
    protected Context mParentContext;
    protected final LayoutInflater mInflater;

    public HanRecyclerAdapterItem(LayoutInflater inflater, ViewGroup parent) {
        this.mInflater = inflater;
        mItemVIew = inflater.inflate(itemViewLayoutId(), parent, false);
        mParentContext = parent == null ? null : parent.getContext();
        myViewHolder = new MyViewHolder(mItemVIew);
    }

    protected String initTag() {
        return getClass().getSimpleName();
    }

    protected abstract int itemViewLayoutId();

    protected abstract void onBindItemData(T datum, int position, int totalCount);

    protected boolean isDisPlayItemAnimation() {
        return false;
    }

    protected Interpolator getAnimationInterpolator() {
        return null;
    }

    protected int getAnimationDuration() {
        return 500;
    }

    protected void onAnimation(float value) {
        // for child
    }

    protected void startValueAnimation() {
        if (getViewHolder() != null) {
            getViewHolder().startAnimation();
        }
    }

    public Context getParentContext() {
        return mParentContext;
    }

    public View getItemVIew() {
        return mItemVIew;
    }

    public MyViewHolder getViewHolder() {
        return myViewHolder;
    }


    public class MyViewHolder extends MyRecyclerViewHolder<T> {
        MyViewHolder(View itemView) {
            super(itemView);
//    --------------------------------------bindView
        }

        @Override
        public void onBindData(T t, int position, int totalCount) {
            onBindItemData(t, position, totalCount);
        }

        @Override
        protected void applyAnimation(float interpolatedTime) {
            onAnimation(interpolatedTime);
        }

        @Override
        protected boolean shouldDisplayAnimation() {
            return isDisPlayItemAnimation();
        }

        @Override
        protected int getDuration() {
            return getAnimationDuration();
        }

        @Override
        public Interpolator getInterpolator() {
            return getAnimationInterpolator();
        }
    }


}
