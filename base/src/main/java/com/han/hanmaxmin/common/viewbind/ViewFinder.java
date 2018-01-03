package com.han.hanmaxmin.common.viewbind;

import android.app.Activity;
import android.view.View;

/**
 * @CreateBy Administrator
 * @Date 2017/12/24  22:14
 * @Description  findViewById 的实现类。
 *
 */

final class ViewFinder {
    private View     view;
    private Activity activity;

    ViewFinder(View view) {
        this.view = view;
    }

    ViewFinder(Activity activity) {
        this.activity = activity;
    }

    public View findViewById(int id) {
        if (view != null) return view.findViewById(id);
        if (activity != null) return activity.findViewById(id);
        return null;
    }

    public View findViewByInfo(ViewInfo viewInfo) {
        return findViewById(viewInfo.value, viewInfo.parentId);

    }

    public View findViewById(int value, int parentId) {
        View pView = null;
        if (parentId > 0) {
            pView = this.findViewById(parentId);//调用当前的 findViewById。
        }

        View view;
        if(pView != null){
            view = pView.findViewById(value);
        }else{
            view = this.findViewById(value);
        }
        return view;
    }
}
