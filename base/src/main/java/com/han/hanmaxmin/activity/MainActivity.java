package com.han.hanmaxmin.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.hantext.aspect.log.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class


MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "MainActivity";
    private TextView thread_main;
    private TextView thread_http;
    private TextView thread_work;
    private TextView thread_single;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HanHelper.getInstance().getScreenHelper().pushActivity(this);
        thread_main = (TextView) findViewById(R.id.thread_main);
        thread_http = (TextView) findViewById(R.id.thread_http);
        thread_work = (TextView) findViewById(R.id.thread_work);
        thread_single = (TextView) findViewById(R.id.thread_single);

        thread_main.setOnClickListener(this);
        thread_http.setOnClickListener(this);
        thread_work.setOnClickListener(this);
        thread_single.setOnClickListener(this);
    }


    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.thread_main://Aspect——Toast
//                toast();
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.thread_http://Aspect——Log
                log();
                Intent intentVideo = new Intent(this, Main3Activity.class);
                startActivity(intentVideo);
                break;
            case R.id.thread_work://Aspect——Intent
                intent();
                break;
            case R.id.thread_single:
                thread();
                break;

        }
    }

    @ThreadPoint(ThreadType.HTTP) private void thread() {
        L.i(TAG, "你点的是Single线程。。。。");
        int mWindowWidth;
        int mWindowHeight;
        Dialog dialog = new Dialog(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        mWindowWidth = displayMetrics.widthPixels;
        mWindowHeight = displayMetrics.heightPixels;
        dialog.setContentView(inflate, new ViewGroup.MarginLayoutParams(mWindowWidth, ViewGroup.MarginLayoutParams.MATCH_PARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setTitle("nihaoo");
        dialog.show();
    }

    @ThreadPoint(ThreadType.WORK) private void intent() {
        L.i(TAG, "你点的是Work线程。。。。");

    }

    @ThreadPoint(ThreadType.MAIN) private void log() {
        L.i(TAG, "你点的是Http线程。。。。");
        String url = "http://www.ipanda.com/kehuduan/PAGE1450172284887217/index.json";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {

            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                L.i(TAG, "execute的数据：" + response.body().string());
            }
        });
        L.i(TAG, "woca");


    }


    @ThreadPoint(ThreadType.MAIN) private void toast() {
        L.i(TAG, "你点的是Main线程。。。。");
    }


    @Override protected void onStart() {
        super.onStart();
    }

    @Log("onRestart") @Override protected void onRestart() {
        super.onRestart();
    }

    @Log("onPause") @Override protected void onPause() {
        super.onPause();
    }

    @Log("onResume") @Override protected void onResume() {
        super.onResume();
    }

    @Log("onStop") @Override protected void onStop() {
        super.onStop();
    }


    @Log("onDestroy") @Override protected void onDestroy() {
        super.onDestroy();
    }


}
