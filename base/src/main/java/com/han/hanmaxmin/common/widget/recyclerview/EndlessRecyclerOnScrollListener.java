package com.han.hanmaxmin.common.widget.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.han.hanmaxmin.common.log.L;

/**
 * Created by ptxy on 2018/3/16.
 * 继承RecyclerView.OnScrollListener,可以监听到是否滑动到页面最底部
 */

public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener implements OnListLoadNextPageListener {

    /**
     * 当前RecyclerView的类型
     */
    protected LayoutManagerType layoutManagerType;

    /**
     * 最后一个位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见Item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;

    /**
     * RecyclerView 的类型
     */
    private RecyclerView.LayoutManager layoutManager;


    /**
     * 从滑动中得到最后一个可见Item的位置
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        L.i( "HanPullRecyclerFragment", "我是onScrolled");

        if(layoutManager == null){
            layoutManager = recyclerView.getLayoutManager();
        }

        if(layoutManagerType == null){
            if(layoutManager instanceof GridLayoutManager){
                layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof LinearLayoutManager){
                layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager){
                layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException("Unsupported LayoutManager Used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }

            switch (layoutManagerType){
                case LinearLayout:
                    lastVisibleItemPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                    break;
                case GridLayout:
                    lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    break;
                case StaggeredGridLayout:
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    if(lastPositions == null){
                        lastPositions =new int[staggeredGridLayoutManager.getSpanCount()];
                    }
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItemPosition = findMax(lastPositions);// 如果是瀑布流布局的话
                    break;
            }
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        L.i( "HanPullRecyclerFragment", "我是onScrollStateChanged");
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount  = layoutManager.getChildCount();// 可见Item的个数
        int totalItemCount = layoutManager.getItemCount();// item的总数
        if((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount -1)){
                onLoadNextPage(recyclerView);//   ---------
        }

    }

    @Override
    public void onLoadNextPage(View view) {

    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions){
            if(value > max){
                max = value;
            }
        }
        return max;
    }

    public enum LayoutManagerType{
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }

}
