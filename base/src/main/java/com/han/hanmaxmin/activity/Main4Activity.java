package com.han.hanmaxmin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.utils.PresenterUtils;
import com.han.hanmaxmin.common.widget.toast.HanToast;
import com.han.hanmaxmin.hantext.proxy.doingproxy.Subject;
import com.han.hanmaxmin.mvp.HanABActivity;

public class Main4Activity extends HanABActivity {

    private TextView textView;

    @Override
    public int layoutId() {
        return R.layout.activity_main4;
    }

    @Override
    public int actionbarLayoutId() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        HanHelper.getInstance().getHttpHelper().create(AuthApi.class);

    }

}
