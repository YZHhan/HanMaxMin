package com.han.hanmaxmin.activity;

import com.han.hanmaxmin.common.aspect.network.method.GET;

/**
 * Created by ptxy on 2018/2/5.
 */

public interface AuthApi {
    @GET("/user/info")void request();
    @GET("/user/info")void request1();
    @GET("/user/info")void request2();
    @GET("/user/info")void request3();

}