package com.han.hanmaxmin.hantext.mvptest.activithy;

import android.os.Bundle;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.hantext.mvptest.MineActivity;
import com.han.hanmaxmin.hantext.mvptest.fragment.DataBaseListFragment;
import com.han.hanmaxmin.hantext.mvptest.fragment.HomePullListFragment;
import com.han.hanmaxmin.mvp.HanABActivity;
import com.han.hanmaxmin.mvp.HanActivity;

/**
 * Created by ptxy on 2018/3/16.
 */

public class HomeActivity extends HanABActivity {
    @Override
    public void initData(Bundle savedInstanceState) {
        L.i("HanMaxMin","我是一个Activity");

        commitFragment(new DataBaseListFragment());
    }

    @Override
    public int layoutId() {
        return super.layoutId();
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
