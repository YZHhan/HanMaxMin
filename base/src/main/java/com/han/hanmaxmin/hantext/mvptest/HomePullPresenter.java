package com.han.hanmaxmin.hantext.mvptest;

import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.greendao.helper.DataBaseHelper;
import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.common.greendao.model.UserInfoDao;
import com.han.hanmaxmin.hantext.httptext.http.AppHttp;
import com.han.hanmaxmin.hantext.httptext.model.resq.ModelApp;
import com.han.hanmaxmin.hantext.mvptest.fragment.DataBaseListFragment;
import com.han.hanmaxmin.hantext.mvptest.fragment.HomePullListFragment;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptxy on 2018/3/16.
 */

public class HomePullPresenter extends HanPresenter <DataBaseListFragment>{

//    @ThreadPoint(ThreadType.HTTP) public void requestData(){
//        AppHttp httpRequest = createHttpRequest(AppHttp.class);
//        ModelApp modelApp1 = httpRequest.requestCheckUpdate();
//        if(isSuccess(modelApp1)){
//            List <ModelApp>  list = new ArrayList<>();
//            for (int i = 0; i < 10; i++){
//                ModelApp modelApp = new ModelApp();
//                modelApp.name = "葡萄学院"+i;
//                list.add(modelApp);
//            }
////            getView().setData(list);
//        }
//
//    }

    @ThreadPoint(ThreadType.HTTP) public void requestData(){
        List<UserInfo> infos = DataBaseHelper.getInstance().getDataBaseUserInfoHelper().queryUser(UserInfoDao.Properties.Age, "18");
        List list = new ArrayList();
        for (UserInfo info : infos){
            list.add(info);
        }
        getView().setData(list);
        getView().addData(list);
    }

}
