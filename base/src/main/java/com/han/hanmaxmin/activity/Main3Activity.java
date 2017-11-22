package com.han.hanmaxmin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.hantext.aspect.toast.Toast;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {
    private Button video_base;
    private Button video_high;
    private Button video_ui;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        video_base = (Button) findViewById(R.id.video_base);
        video_high = (Button) findViewById(R.id.video_high);
        video_ui = (Button) findViewById(R.id.video_ui);

        video_base.setOnClickListener(this);
        video_high.setOnClickListener(this);
        video_ui.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_base:
                toastBase();
                break;
            case R.id.video_high:
                toastHigh();
                break;
            case R.id.video_ui:
                toastUi();
                break;

        }
    }

    @Toast("我是基础的") private void toastBase() {
    }
    @Toast("我是高级的") private void toastHigh() {
    }
    @Toast("我是UI的") private void toastUi() {
    }
}
