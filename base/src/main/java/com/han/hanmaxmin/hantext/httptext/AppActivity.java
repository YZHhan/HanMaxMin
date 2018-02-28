package com.han.hanmaxmin.hantext.httptext;

import android.appwidget.AppWidgetProvider;
import android.os.Bundle;

import com.han.hanmaxmin.hantext.httptext.http.AppHttp;
import com.han.hanmaxmin.hantext.httptext.presenter.AppParameterPresenter;
import com.han.hanmaxmin.mvp.HanABActivity;
import com.han.hanmaxmin.mvp.HanActivity;
import com.han.hanmaxmin.mvp.fragment.hanifragment.HanListFragment;
import com.han.hanmaxmin.mvp.fragment.hanifragment.HanPullListFragment;

import java.io.IOException;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ptxy on 2018/2/27.
 */

public class AppActivity extends HanABActivity<AppParameterPresenter> {


    @Override
    public void initData(Bundle savedInstanceState) {
        AppParameterPresenter presenter = getPresenter();
        presenter.requestApp();

//        textOkHttp();

    }

    private void textOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("")
                .build();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {// 异步方法进行调用。
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.body().string();//得到字符串
                response.body().bytes();// 得到byte数组
                response.body().byteStream();//得到流


            }
        });


    }


    @Override
    public int actionbarLayoutId() {
        return 0;
    }
}
