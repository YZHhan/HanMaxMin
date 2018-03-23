package com.han.hanmaxmin.hantext.mvptest.fragment;

import android.os.Bundle;

import com.han.hanmaxmin.common.greendao.helper.DataBaseHelper;
import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.common.greendao.model.UserInfoDao;
import com.han.hanmaxmin.hantext.httptext.model.BaseModel;
import com.han.hanmaxmin.hantext.mvptest.adapter.DataBaseAdapterItem;
import com.han.hanmaxmin.hantext.mvptest.model.ModelUserInfo;
import com.han.hanmaxmin.mvp.adapter.HanListAdapterItem;
import com.han.hanmaxmin.mvp.fragment.HanListFragment;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptxy on 2018/3/22.
 */

public class DataBaseListFragment extends HanListFragment <HanPresenter, UserInfo> {
    @Override
    public void initData(Bundle savedInstanceState) {
        getListView().setDividerHeight(10);
        List<UserInfo> infos = DataBaseHelper.getInstance().getDataBaseUserInfoHelper().queryUser(UserInfoDao.Properties.Age, "18");
        List list = new ArrayList();
        for (UserInfo info : infos){
            list.add(info);
        }
        setData(list);

    }

    @Override
    public HanListAdapterItem<UserInfo> getListAdapterItem(int type) {
        return new DataBaseAdapterItem();
    }

}
