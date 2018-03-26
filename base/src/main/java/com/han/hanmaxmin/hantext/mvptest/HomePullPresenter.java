package com.han.hanmaxmin.hantext.mvptest;

import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.greendao.helper.DataBaseHelper;
import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.common.greendao.model.UserInfoDao;
import com.han.hanmaxmin.common.model.HanModel;
import com.han.hanmaxmin.common.widget.listview.LoadingFooter;
import com.han.hanmaxmin.hantext.httptext.http.AppHttp;
import com.han.hanmaxmin.hantext.httptext.model.resq.ModelApp;
import com.han.hanmaxmin.hantext.mvptest.fragment.DataBaseListFragment;
import com.han.hanmaxmin.hantext.mvptest.fragment.HomePullListFragment;
import com.han.hanmaxmin.mvp.fragment.HanPullRecyclerFragment;
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

    private int page;

    @ThreadPoint(ThreadType.HTTP) public void requestData(boolean isLoad){
        if(isLoad){
            if(page < 2) return;
            List list = new ArrayList();
            for (int i = 0; i < 10; i++){
              UserInfo userInfo = new UserInfo();
              userInfo.setName("我是00 "+i +1);
              userInfo.setAge("我今年"+i);
              userInfo.setLike("我想对你说，");
              list.add(userInfo);
              paging(userInfo);
            }
            page ++;
            getView().addData(list);
        } else {
            page = 1;
            List list = new ArrayList();
            for (int i = 0; i < 10; i++){
                UserInfo userInfo = new UserInfo();
                userInfo.setName("我是00 "+i +1);
                userInfo.setAge("我今年"+i);
                userInfo.setLike("我想对你说，");
                list.add(userInfo);
                paging(userInfo);
            }
            page = 2;
            getView().setData(list);
        }

    }

    @Override
    public void paging(HanModel model) {
        if(model != null){
            HanPullRecyclerFragment view = getView();
            view.setLoadingState(LoadingFooter.State.Normal);
        }
    }
}
