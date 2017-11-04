package com.han.hanmaxmin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.hantext.aspect.intent.Intent;
import com.han.hanmaxmin.hantext.aspect.log.Log;
import com.han.hanmaxmin.hantext.aspect.toast.Toast;

public class




MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "MainActivity";
    private TextView main_toast;
    private TextView main_log;
    private TextView main_intent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HanHelper.getInstance().getScreenHelper().pushActivity(this);
        main_toast = (TextView) findViewById(R.id.main_toast);
        main_log = (TextView) findViewById(R.id.main_log);
        main_intent = (TextView) findViewById(R.id.main_intent);

        main_toast.setOnClickListener(this);
        main_log.setOnClickListener(this);
        main_intent.setOnClickListener(this);
    }


    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_toast://Aspect——Toast
                toast();
                break;
            case R.id.main_log://Aspect——Log
                log();
                break;
            case R.id.main_intent://Aspect——Intent
                intent();
                break;

        }
    }
    @Intent(Main2Activity.class)
    private void intent() {
        L.i("Toast","___________________----------Aspect_Intent");
    }

    @Log("我是被注解进来的")
    private void log() {
        L.i("Toast","___________________----------Aspect_Log");
    }


    @Toast("注解Toast")
    private void toast() {
        L.i("Toast","___________________----------Aspect_Toast");
    }




    @Override protected void onStart() {
        super.onStart();
    }

    @Log("onRestart")
    @Override protected void onRestart() {
        super.onRestart();
    }

    @Log("onPause")
    @Override protected void onPause() {
        super.onPause();
    }

    @Log("onResume")
    @Override protected void onResume() {
        super.onResume();
    }
    @Log("onStop")
    @Override protected void onStop() {
        super.onStop();
    }


    @Log("onDestroy")
    @Override protected void onDestroy() {
        super.onDestroy();
    }



}
