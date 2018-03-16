package com.han.hanmaxmin.hantext.mvptest.fragment;

import android.os.Bundle;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.widget.ptr.PtrUIHandler;
import com.han.hanmaxmin.common.widget.refreshHeader.BeautyCircleRefreshHeader;
import com.han.hanmaxmin.hantext.httptext.model.resq.ModelApp;
import com.han.hanmaxmin.hantext.mvptest.HomePullPresenter;
import com.han.hanmaxmin.hantext.mvptest.adapter.HomePullAdapterItem;
import com.han.hanmaxmin.mvp.adapter.HanListAdapterItem;
import com.han.hanmaxmin.mvp.fragment.HanFragment;
import com.han.hanmaxmin.mvp.fragment.HanPullListFragment;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

/**
 * Created by ptxy on 2018/3/16.
 */

public class HomePullListFragment extends HanFragment<HomePullPresenter> {
    private BeautyCircleRefreshHeader refreshHeader;

//    @Override
//    public PtrUIHandler getPtrUIHandlerView() {
//        if (refreshHeader == null) {
//            refreshHeader = new BeautyCircleRefreshHeader(getContext());
//        }
//        return refreshHeader;
//    }
//
//    @Override
//    public void onRefresh() {
//
//    }
//
//    @Override
//    public void onLoad() {
//
//    }

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        L.i("HanMaxMin","我是一个Fragment");
        getPresenter().requestData();
        showContentView();
    }

//    @Override
//    public HanListAdapterItem getListAdapterItem(int type) {
//        return new HomePullAdapterItem();
//    }
}
