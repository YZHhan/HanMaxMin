package com.han.hanmaxmin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.HanNoDoubleClickListener;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.utils.PresenterUtils;
import com.han.hanmaxmin.common.widget.toast.HanToast;
import com.han.hanmaxmin.hantext.proxy.doingproxy.Subject;
import com.han.hanmaxmin.mvp.HanABActivity;

public class Main4Activity extends HanABActivity {

    private TextView textView;
    private TextView textView1;

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
        textView = (TextView) findViewById(R.id.textText);
        textView1 = (TextView) findViewById(R.id.textText1);
        textView.setOnClickListener(new HanNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                L.i("onClick","223456765412345678-------------");
            }
        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i("onClick","你好你我诶见ISF四很烦ID合法is覅的说法 -------------");

            }
        });
        HanHelper.getInstance().getHttpHelper().create(AuthApi.class);

    }

}
