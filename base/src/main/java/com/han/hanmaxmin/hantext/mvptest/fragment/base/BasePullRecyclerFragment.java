package com.han.hanmaxmin.hantext.mvptest.fragment.base;

import com.han.hanmaxmin.common.widget.ptr.PtrUIHandler;
import com.han.hanmaxmin.common.widget.refreshHeader.BeautyCircleRefreshHeader;
import com.han.hanmaxmin.mvp.adapter.HanRecyclerAdapterItem;
import com.han.hanmaxmin.mvp.fragment.HanIPullRecyclerFragment;
import com.han.hanmaxmin.mvp.fragment.HanPullRecyclerFragment;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * Created by ptxy on 2018/3/23.
 */

public abstract class BasePullRecyclerFragment<P extends HanPresenter, D> extends HanPullRecyclerFragment<P, D> implements HanIPullRecyclerFragment <D> {

    private BeautyCircleRefreshHeader humanRefreshHeader;

    /**
     * 设置刷新头
     */
    @Override public PtrUIHandler getPtrUIHandlerView() {
        if (humanRefreshHeader == null) {
            humanRefreshHeader = new BeautyCircleRefreshHeader(getContext());
        }
        return humanRefreshHeader;
    }


}
