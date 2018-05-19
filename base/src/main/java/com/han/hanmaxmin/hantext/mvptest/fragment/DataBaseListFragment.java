package com.han.hanmaxmin.hantext.mvptest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.hantext.mvptest.HomePullPresenter;
import com.han.hanmaxmin.hantext.mvptest.adapter.DataBaseAdapterItem;
import com.han.hanmaxmin.hantext.mvptest.fragment.base.BasePullRecyclerFragment;
import com.han.hanmaxmin.mvp.adapter.HanRecyclerAdapterItem;

/**
 * Created by ptxy on 2018/3/22.
 */

public class DataBaseListFragment extends BasePullRecyclerFragment<HomePullPresenter, UserInfo> {
    @Override
    public void initData(Bundle savedInstanceState) {
        getPresenter().requestData(false);
    }

    @Override
    public void loading(int resId) {

    }

    @Override
    public void loading(int resId, boolean cancelAble) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoad() {
//    getPresenter().requestData(true);
    }

    @Override
    public HanRecyclerAdapterItem<UserInfo> getRecyclerAdapterItem(LayoutInflater inflater, ViewGroup viewGroup, int type) {
        return new DataBaseAdapterItem(inflater, viewGroup);
    }
}
