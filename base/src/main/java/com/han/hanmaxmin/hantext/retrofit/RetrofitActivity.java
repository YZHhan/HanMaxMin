package com.han.hanmaxmin.hantext.retrofit;

import android.os.Bundle;

import com.han.hanmaxmin.hantext.httptext.http.AppHttp;
import com.han.hanmaxmin.mvp.HanActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @CreatedBy ptxy
 * @DataOn 2018/4/27
 * @Description
 * Retrofit 是一个基于RESTFul的HTTP网络请求库，底层话基于OkHttp的。可以说是对OkHttp的二次封装。特点使用简单通过注解配置网路请求参数
 * 支持同步异步，支持多种数据解析（Gson, Json，Xml），提供对RxJava的支持。采用大量的设计模式（构建者模式，动态代理模式，静态代理模式，
 * 适配器模式，工厂模式）网路请求的工作本质是OkHttp完成的，而Retrofit仅负责网络请求接口的封装。____其实就是用Retrofit接口层封装请求
 * 参数、header、url等信息，然后又OKHttp完成后续的操作，在服务端返回数据，OkHttp将原始的数据交给Retrofit。根据需求进行解析。
 * 普通的流程 ： 配置网络请求参数，创建网络请求对象，发送网路请求，服务器返回数据，解析数据，处理返回数据。
 *  Retrofit 流程：配置网络请求参数（通过注解），创建网络请求对象——适配到具体的Call，发送网路请求，解析数据，切换线程。
 *
 *
 *
 *
 */

public class RetrofitActivity extends HanActivity {
    @Override
    public void initData(Bundle savedInstanceState) {
        //使用建造者模式创建Retrofit实例
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("")//
                .addConverterFactory(GsonConverterFactory.create()).build();

        AppHttp appHttp = retrofit.create(AppHttp.class);
        appHttp.requestCheckUpdate();

    }

    @Override
    public boolean isShowBackButtonInDefaultView() {
        return false;
    }
}
