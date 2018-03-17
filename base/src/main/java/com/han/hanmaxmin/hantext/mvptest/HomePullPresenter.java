package com.han.hanmaxmin.hantext.mvptest;

import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.hantext.httptext.model.resq.ModelApp;
import com.han.hanmaxmin.hantext.mvptest.fragment.HomePullListFragment;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptxy on 2018/3/16.
 */

public class HomePullPresenter extends HanPresenter <HomePullListFragment>{

    @ThreadPoint(ThreadType.HTTP) public void requestData(){
        List <ModelApp>  list = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            ModelApp modelApp = new ModelApp();
            modelApp.name = "葡萄学院"+i;
            list.add(modelApp);
        }
        getView().setData(list);
    }

}
