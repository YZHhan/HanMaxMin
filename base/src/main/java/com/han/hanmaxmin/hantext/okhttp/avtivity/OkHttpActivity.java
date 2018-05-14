package com.han.hanmaxmin.hantext.okhttp.avtivity;

import android.os.Bundle;

import com.han.hanmaxmin.mvp.HanActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/5/4
 * @Description
 */

public class OkHttpActivity extends HanActivity{
    @Override
    public void initData(Bundle savedInstanceState) {


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com")
                .build();
        try {
            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.isSuccessful()) {
                System.out.println("成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isShowBackButtonInDefaultView() {
        return false;
    }
}
