package com.han.hanmaxmin.common.utils.deeplink;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.han.hanmaxmin.mvp.HanABActivity;

/**
 * Created by ptxy on 2018/1/31.
 */

public class WebViewActivity extends HanABActivity  {
    @Override
    public int actionbarLayoutId() {
        return 0;
    }

    @Override
    public void setActivityTitle(Object value, int code) {

    }

    @Override
    public boolean isBlackIconStatusBar() {
        return false;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initDataWhenDelay() {

    }



    @Override
    public boolean isShowBackButtonInDefaultView() {
        return false;
    }

    @Override
    public void commitFragment(Fragment oldFragment, int layoutId, Fragment fragment, String trg) {

    }
}
