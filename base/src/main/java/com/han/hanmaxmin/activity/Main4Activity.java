package com.han.hanmaxmin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.mvp.HanABActivity;

public class Main4Activity extends HanABActivity {

    private TextView textView;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
    }

    @Override
    public int actionbarLayoutId() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public Object getPresenter() {
        return null;
    }
}
