package com.han.hanmaxmin.hantext.httptext.http;

import com.han.hanmaxmin.common.aspect.network.method.GET;
import com.han.hanmaxmin.hantext.httptext.model.ModelApp;

/**
 * Created by ptxy on 2018/2/27.
 */

public interface AppHttp {

    /**
     * app检查升级
     */
    @GET("/app/checkUpdate") ModelApp requestCheckUpdate();
}