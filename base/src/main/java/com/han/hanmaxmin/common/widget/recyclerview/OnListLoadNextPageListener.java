package com.han.hanmaxmin.common.widget.recyclerview;

import android.view.View;

/**
 * Created by ptxy on 2018/3/16.
 * RecyclerView/ListView/GridView 滑动加载下一页时的回调接口
 */

public interface OnListLoadNextPageListener {

    /**
     * 开始加载下一页
     * @param view 当前的RecyclerView/ListView/GridView
     */
    void onLoadNextPage(View view);
}
