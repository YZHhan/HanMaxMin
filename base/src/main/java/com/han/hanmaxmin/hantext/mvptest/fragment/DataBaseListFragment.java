package com.han.hanmaxmin.hantext.mvptest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.han.hanmaxmin.common.greendao.helper.DataBaseHelper;
import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.common.greendao.model.UserInfoDao;
import com.han.hanmaxmin.common.widget.ptr.PtrUIHandler;
import com.han.hanmaxmin.hantext.httptext.model.BaseModel;
import com.han.hanmaxmin.hantext.mvptest.adapter.DataBaseAdapterItem;
import com.han.hanmaxmin.hantext.mvptest.fragment.base.BasePullRecyclerFragment;
import com.han.hanmaxmin.hantext.mvptest.model.ModelUserInfo;
import com.han.hanmaxmin.mvp.adapter.HanListAdapterItem;
import com.han.hanmaxmin.mvp.adapter.HanRecyclerAdapterItem;
import com.han.hanmaxmin.mvp.fragment.HanListFragment;
import com.han.hanmaxmin.mvp.fragment.HanPullRecyclerFragment;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptxy on 2018/3/22.
 */

public class DataBaseListFragment extends BasePullRecyclerFragment<HanPresenter, UserInfo> {
    @Override
    public void initData(Bundle savedInstanceState) {
        openPullRefreshing();
        List<UserInfo> infos = DataBaseHelper.getInstance().getDataBaseUserInfoHelper().queryUser(UserInfoDao.Properties.Age, "18");
        List list = new ArrayList();
        for (UserInfo info : infos){
            list.add(info);
        }
        setData(list);

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public HanRecyclerAdapterItem<UserInfo> getRecyclerAdapterItem(LayoutInflater inflater, ViewGroup viewGroup, int type) {
        return new DataBaseAdapterItem(inflater, viewGroup);
    }
}
