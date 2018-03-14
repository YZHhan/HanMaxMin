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

public abstract class MyRecyclerViewHolder <T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    private int mPosition;
    private int mTotalCount;
    private ValueAnimator valueAnimator;
    private AdapterView.OnItemClickListener mClickListener;
    private AdapterView.OnItemLongClickListener mLongClickListener;
    private long duration;


    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        if(shouldDisplayAnimation()){
            initAnimation();
        }
    }

    private void initAnimation() {
        valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(getDuration());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                applyAnimation(value);
            }
        });
        if(getInterpolator() != null){
            valueAnimator.setInterpolator(getInterpolator());
        }
    }

    protected void startAnimation(){
        if(valueAnimator != null){
            initAnimation();
        }
        valueAnimator.start();
    }

    /**
     *
     * @param t 对应索引item的数据   单条数据
     * @param position current index
     * @param totalCount item total
     */
    public abstract void onBindData(T t, int position, int totalCount);

    /**
     * 设置当前item索引  和 item的总数。
     */
    public void setPosition(int position, int totalCount){
        this.mPosition = position;
        this.mTotalCount = totalCount;
    }


    /**
     * 默认Alpha动画
     */
    protected abstract void applyAnimation(float interpolatedTime);

    protected abstract boolean shouldDisplayAnimation();


    @Override
    public void onClick(View v) {
        if(mClickListener != null)mClickListener.onItemClick(null, v, mPosition, -1);
    }

    @Override
    public boolean onLongClick(View v) {
        return mClickListener != null && mLongClickListener.onItemLongClick(null, v, mPosition, -1);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        this.mClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener){
        this.mLongClickListener = onItemLongClickListener;
    }

    protected abstract int getDuration();

    public abstract Interpolator getInterpolator();
}
