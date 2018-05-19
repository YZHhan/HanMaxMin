package com.han.hanmaxmin.hantext.mvptest.fragment;

import android.os.Bundle;
import android.view.View;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.greendao.helper.DataBaseHelper;
import com.han.hanmaxmin.common.greendao.model.StringConverter;
import com.han.hanmaxmin.common.greendao.model.User;
import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.viewbind.annotation.OnClick;
import com.han.hanmaxmin.common.widget.toast.HanToast;
import com.han.hanmaxmin.hantext.mvptest.HomePullPresenter;
import com.han.hanmaxmin.mvp.fragment.HanFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptxy on 2018/3/16.
 */

public class HomePullListFragment extends HanFragment<HomePullPresenter> {

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        L.i("HanMaxMin","我是一个Fragment");
        showContentView();
    }

    @OnClick({ R.id.thread_main, R.id.thread_http, R.id.thread_work, R.id.thread_single, R.id.tv_intent})public void onClick(View view){
        switch (view.getId()){
            case R.id.thread_main:
              HanToast.show("我是插入");
                UserInfo userInfo = new UserInfo();
                userInfo.setName("yinzhan");
                userInfo.setAge("18");
                userInfo.setSex("nan");
                DataBaseHelper.getInstance().getDataBaseUserInfoHelper().insertOrReplace(userInfo);

                break;
            case R.id.thread_http:
                HanToast.show("我是查询");
                break;
            case R.id.thread_work:

                break;
            case R.id.thread_single:
                    HanToast.show("删除数据库");
                break;

            case R.id.tv_intent:
commitFragment(new DataBaseListFragment());
                break;


        }
    }


    @Override
    public boolean isOpenViewState() {
        return true;
    }

    @Override
    public void loading(int resId) {

    }

    @Override
    public void loading(int resId, boolean cancelAble) {

    }
}
