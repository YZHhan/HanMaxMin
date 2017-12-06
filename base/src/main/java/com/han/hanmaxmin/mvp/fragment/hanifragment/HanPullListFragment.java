package com.han.hanmaxmin.mvp.fragment.hanifragment;

import android.view.LayoutInflater;
import android.view.View;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.widget.ptr.PtrFrameLayout;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * @CreateBy Administrator
 * @Date 2017/11/23  15:37
 * @Description
 */

public abstract class HanPullListFragment<T extends HanPresenter, D> extends HanListFragment<T, D> implements HanIPullListFragment<D> {
    private PtrFrameLayout mPtrFrameLayout;
    private boolean canLoadingMore=true;

    /**
     * The fragments of XML layout.
     * Can manually layout file, also can use the default file
     *
     */
    @Override public int layoutId() {
        return (!isOpenViewState() && (getTopLayout() > 0 || getBottomLayout() > 0))? R.layout.han_fragment_pull_listview_with_top_bottom : R.layout.han_fragment_pull_listview;
    }

    @Override public int getFootLayout() {
        return HanHelper.getInstance().getApplication().listFooterLayoutId();
    }

    @Override protected View initView(LayoutInflater inflater) {
        View view=super.initView(inflater);
        initPtrFrameLayout(inflater);
        return super.initView(inflater);
    }

    private  void initPtrFrameLayout(LayoutInflater inflater){

    }
}
