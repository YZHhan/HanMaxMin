package com.han.hanmaxmin.activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.listener.HanNoDoubleClickListener;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.hantext.httptext.AppActivity;
import com.han.hanmaxmin.hantext.view.LineTag;
import com.han.hanmaxmin.mvp.HanABActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Main4Activity extends HanABActivity {

    private TextView textView;
    private TextView textView1;
    private LineTag lineTag;

    @Override
    public int layoutId() {
        return R.layout.activity_main4view;
    }

    @Override
    public int actionbarLayoutId() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        lineTag = (LineTag) findViewById(R.id.line_tag);
//        textView = (TextView) findViewById(R.id.textText);
//        textView1 = (TextView) findViewById(R.id.textText1);
//        textView.setOnClickListener(new HanNoDoubleClickListener() {
//            @Override
//            protected void onNoDoubleClick(View v) {
//                L.i("onClick","223456765412345678-------------");
//                intent2Activity(AppActivity.class);
//            }
//        });
//
//        textView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                L.i("onClick","你好你我诶见ISF四很烦ID合法is覅的说法 -------------");
//
//            }
//        });

        initTextView(lineTag);
    }


    private void initTextView(LineTag lineTag) {
        List list = new ArrayList();
        list.add("我是尹自含哈哈哈哈   ");
        list.add("我是尹  ");
        list.add("尹自含  ");
        list.add("我自含");
        LinearLayoutCompat linearLayout = new LinearLayoutCompat(this);
        for (int i = 0; i<list.size();i++){
            TextView textView=new TextView(this);
            textView.setTextSize(20);

             String o = (String) list.get(i);
            textView.setText(o);
            linearLayout.addView(textView);

        }
        lineTag.addView(linearLayout);

    }


}
