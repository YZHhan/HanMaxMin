package com.han.hanmaxmin.hantext.httptext;

import android.appwidget.AppWidgetProvider;
import android.os.Bundle;

import com.han.hanmaxmin.hantext.httptext.http.AppHttp;
import com.han.hanmaxmin.hantext.httptext.presenter.AppParameterPresenter;
import com.han.hanmaxmin.mvp.HanABActivity;
import com.han.hanmaxmin.mvp.HanActivity;
import com.han.hanmaxmin.mvp.fragment.hanifragment.HanListFragment;
import com.han.hanmaxmin.mvp.fragment.hanifragment.HanPullListFragment;

/**
 * Created by ptxy on 2018/2/27.
 */

public class AppActivity extends HanABActivity<AppParameterPresenter> {


    @Override
    public void initData(Bundle savedInstanceState) {
        AppParameterPresenter presenter = getPresenter();
        presenter.requestApp();
    }



    @Override
    public int actionbarLayoutId() {
        return 0;
    }
}
