package com.han.hanmaxmin.common.widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by ptxy on 2018/3/7.
 */

public class MyScrollView extends ScrollView {

    private OnScrollListener onScrollListener;


    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //设置滚动接口
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    /**
     * 整体的高度，注意是整体，包括在显示区域之外的。
     */
    @Override
    protected int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }



    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener!=null){
            onScrollListener.onScroll(t);//获取了ScrollView的Y轴上滑动的距离
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }






    public interface OnScrollListener{
        /*
        * 回调方法
        * 回调该控件Y方向的变化距离
        * **/
        public void onScroll(int scrollY);
    }
}
