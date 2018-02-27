package com.han.hanmaxmin.hantext.httptext.presenter;

import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.hantext.httptext.HttpPresenter;
import com.han.hanmaxmin.hantext.httptext.AppActivity;
import com.han.hanmaxmin.hantext.httptext.http.AppHttp;
import com.han.hanmaxmin.hantext.httptext.model.ModelApp;

/**
 * Created by ptxy on 2018/2/27.
 */

public class AppParameterPresenter extends HttpPresenter <AppActivity> {

    @ThreadPoint(ThreadType.HTTP) public void requestApp(){
        AppHttp httpRequest = createHttpRequest(AppHttp.class);
        ModelApp modelApp = httpRequest.requestCheckUpdate();
        if(isSuccess(modelApp)){
            L.i("AppParameterPresenter", modelApp.downloadUrl + modelApp.updateTitle + modelApp.versionCode);
        }
    }
}
