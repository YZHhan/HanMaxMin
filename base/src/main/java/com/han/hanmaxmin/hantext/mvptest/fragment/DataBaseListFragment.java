package com.han.hanmaxmin.hantext.mvptest.fragment;

import android.os.Bundle;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.common.viewbind.annotation.Bind;
import com.han.hanmaxmin.common.widget.refreshHeader.BeautyCircleRefreshHeader;
import com.han.hanmaxmin.common.widget.toast.HanToast;
import com.han.hanmaxmin.hantext.mvptest.HomePullPresenter;
import com.han.hanmaxmin.hantext.mvptest.adapter.DataBaseAdapterItem;
import com.han.hanmaxmin.hantext.mvptest.fragment.base.BasePullListViewFragment;
import com.han.hanmaxmin.mvp.adapter.HanListAdapterItem;

/**
 * Created by ptxy on 2018/3/22.
 */

public class DataBaseListFragment extends BasePullListViewFragment<HomePullPresenter, UserInfo> {

    @Bind(R.id.bcrh_refresh)
    BeautyCircleRefreshHeader bcrh_refresh;

    @Override
    public void initData(Bundle savedInstanceState) {
        getPtrFragment().addPtrUIHandler(bcrh_refresh);
        getPresenter().requestData(false);
    }

    @Override
    public int getHeaderLayout() {
        return R.layout.data_listview_fragment;
    }

    @Override
    public void loading(int resId) {

    }

    @Override
    public void loading(int resId, boolean cancelAble) {

    }

    @Override
    public void onRefresh() {
        HanToast.show("刷新");
        getPresenter().requestData(false);

    }

    @Override
    public void onLoad() {
    getPresenter().requestData(true);
        HanToast.show("加载");
    }


    @Override
    public HanListAdapterItem<UserInfo> getListAdapterItem(int type) {
        return new DataBaseAdapterItem();
    }

}
