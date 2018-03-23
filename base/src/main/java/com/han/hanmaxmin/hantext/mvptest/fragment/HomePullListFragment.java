package com.han.hanmaxmin.hantext.mvptest.fragment;

import android.os.Bundle;
import android.view.View;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.greendao.helper.DataBaseHelper;
import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.common.greendao.model.UserInfoDao;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.viewbind.annotation.OnClick;
import com.han.hanmaxmin.common.widget.ptr.PtrUIHandler;
import com.han.hanmaxmin.common.widget.refreshHeader.BeautyCircleRefreshHeader;
import com.han.hanmaxmin.common.widget.toast.HanToast;
import com.han.hanmaxmin.hantext.httptext.model.resq.ModelApp;
import com.han.hanmaxmin.hantext.mvptest.HomePullPresenter;
import com.han.hanmaxmin.hantext.mvptest.adapter.HomePullAdapterItem;
import com.han.hanmaxmin.mvp.adapter.HanListAdapterItem;
import com.han.hanmaxmin.mvp.fragment.HanFragment;
import com.han.hanmaxmin.mvp.fragment.HanListFragment;
import com.han.hanmaxmin.mvp.fragment.HanPullListFragment;
import com.han.hanmaxmin.mvp.presenter.HanPresenter;

import java.util.List;

/**
 * Created by ptxy on 2018/3/16.
 */

public class HomePullListFragment extends HanFragment<HomePullPresenter> {

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        L.i("HanMaxMin","我是一个Fragment");
        showContentView();
    }

    @OnClick({ R.id.thread_main, R.id.thread_http, R.id.thread_work, R.id.thread_single, R.id.tv_intent})public void onClick(View view){
        switch (view.getId()){
            case R.id.thread_main:
                HanToast.show("我看是插入");
                UserInfo userInfo = new UserInfo();
                userInfo.setName("ZZZZZZ");
                userInfo.setLike("孙大圣");
                userInfo.setAge("18");
                userInfo.setId(5);
                userInfo.setNumber("3214567543");
                userInfo.setSpeak("我爱你");
                userInfo.setTeacher_tag_t1("最优秀女朋友");
                userInfo.setTeacher_tag_t4("最智障的人");
                DataBaseHelper.getInstance().getDataBaseUserInfoHelper().insertOrReplace(userInfo);
                break;
            case R.id.thread_http:
                HanToast.show("我看是读取");
//                UserInfo info = DataBaseHelper.getInstance().getDataBaseUserInfoHelper().queryByCurrentUser(UserInfoDao.Properties.Name, "尹自含");
                List<UserInfo> info1 = DataBaseHelper.getInstance().getDataBaseUserInfoHelper().queryUser(UserInfoDao.Properties.Age, "18");
                if(null == info1){
                    L.i("HanMaxMin", "数据库为空，哈哈");
                } else {
                    for (UserInfo info : info1){
                        L.i("HanMaxMin", "我是：" +info.getName() +"    年龄 ："+ info.getAge() + "    我喜欢 :" +info.getLike() +"    联系方式:" +info.getNumber()  +"  我想说 ： "+info.getSpeak()  +  "           "+info.getId()+"   标签 ：" +info.getTeacher_tag_t1());
                    }
                }
                break;

            case R.id.thread_work:
                HanToast.show("我看是插入");
                UserInfo userInfo1 = new UserInfo();
                userInfo1.setName("尹自含");
                userInfo1.setLike("紫霞仙子");
                userInfo1.setAge("18");
                userInfo1.setNumber("3214567543");
                userInfo1.setSpeak("我爱你");
                userInfo1.setId(3);
                userInfo1.setTeacher_tag_t1("最优秀男朋友");
                userInfo1.setTeacher_tag_t4("最棒的");
                DataBaseHelper.getInstance().getDataBaseUserInfoHelper().insertOrReplace(userInfo1);
                break;
            case R.id.thread_single:
                    HanToast.show("删除数据库");
                List<UserInfo> infos= DataBaseHelper.getInstance().getDataBaseUserInfoHelper().queryUser(UserInfoDao.Properties.Age, "18");
            DataBaseHelper.getInstance().getDataBaseUserInfoHelper().delete(infos);
                break;

            case R.id.tv_intent:
commitFragment(new DataBaseListFragment());
                break;


        }
    }


    @Override
    public boolean isOpenViewState() {
        return true;
    }
}
