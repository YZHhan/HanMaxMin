package com.han.hanmaxmin.hantext.mvptest.activithy;

import android.Manifest;
import android.os.Bundle;
import android.widget.ImageView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.aspect.permission.Permission;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.viewbind.annotation.Bind;
import com.han.hanmaxmin.common.widget.refreshHeader.BeautyCircleDrawable;
import com.han.hanmaxmin.hantext.mvptest.MineActivity;
import com.han.hanmaxmin.hantext.mvptest.fragment.DataBaseListFragment;
import com.han.hanmaxmin.hantext.mvptest.fragment.HomePullListFragment;
import com.han.hanmaxmin.mvp.HanABActivity;
import com.han.hanmaxmin.mvp.HanActivity;

/**
 * Created by ptxy on 2018/3/16.
 */

public class HomeActivity extends HanABActivity {

    @Permission(value = {//
            Manifest.permission.WRITE_EXTERNAL_STORAGE,//
            Manifest.permission.ACCESS_COARSE_LOCATION,//
            Manifest.permission.ACCESS_FINE_LOCATION, //
            Manifest.permission.READ_PHONE_STATE}//
    )
    @Override
    public void initData(Bundle savedInstanceState) {
        L.i("HanMaxMin","我是一个Activity");
        commitFragment(new DataBaseListFragment());
    }

    @Override
    public boolean isShowBackButtonInDefaultView() {
        return false;
    }

    @Override
    public int actionbarLayoutId() {
        return R.layout.actionbar_title_back;
    }

}
