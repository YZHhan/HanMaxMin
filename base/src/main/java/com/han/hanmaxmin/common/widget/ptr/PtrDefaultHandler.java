package com.han.hanmaxmin.common.widget.ptr;

import android.os.Build;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by ptxy on 2018/3/12.
 * 检查是否可以刷新。   直接用AbsListView  列表类的基础。
 */

public abstract class PtrDefaultHandler implements PtrHandler {

    public static boolean canChildScrollUp(View view){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH){//14
            if(view instanceof AbsListView){
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        &&(absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return view.getScaleY() > 0;
            }
        } else {
            return view.canScrollVertically(-1);
        }

    }

    /**
     * Default implement for check can perform pull to refresh
     * 检查的默认实现可以执行拉刷新。
     */
    public static boolean checkContentCanBePulledDown(PtrFrameLayout frame, View content, View header){
        return !canChildScrollUp(content);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return checkContentCanBePulledDown(frame, content, header);
    }

}
