package com.han.hanmaxmin.hantext.mvptest.fragment;

import android.Manifest;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.bumptech.glide.annotation.GlideModule;
import com.example.mylibrary.common.Demo;
import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.aspect.permission.NeedPermission;
import com.han.hanmaxmin.common.aspect.permission.PermissionCancel;
import com.han.hanmaxmin.common.aspect.permission.PermissionDenied;
import com.han.hanmaxmin.common.aspect.permission.bean.CancelBean;
import com.han.hanmaxmin.common.aspect.permission.bean.DenyBean;
import com.han.hanmaxmin.common.greendao.helper.DataBaseHelper;
import com.han.hanmaxmin.common.greendao.model.User;
import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.common.greendao1.dao.StudentBaseDao;
import com.han.hanmaxmin.common.greendao1.model.Student;
import com.han.hanmaxmin.common.greendao1.model.StudentDao;
import com.han.hanmaxmin.common.greendao1.model.UserDao;
import com.han.hanmaxmin.common.greendao1.utils.DataBaseUtils;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.viewbind.annotation.OnClick;
import com.han.hanmaxmin.common.widget.toast.HanToast;
import com.han.hanmaxmin.hantext.mvptest.HomePullPresenter;
import com.han.hanmaxmin.hantext.mvptest.SuperPopupwindow;
import com.han.hanmaxmin.mvp.adapter.HanListAdapterItem;
import com.han.hanmaxmin.mvp.common.UserConfig;
import com.han.hanmaxmin.mvp.fragment.HanFragment;
import com.han.hanmaxmin.mvp.fragment.HanPullListFragment;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
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
            HanToast.show("我是插入");
            UserInfo userInfo= new UserInfo();
            userInfo.setName("日复一日");
            userInfo.setAge("2");
            userInfo.setLike("日久生情");
            List<String> list = new ArrayList<>();
            list.add("123");
            list.add("234");
            list.add("345");
            list.add("456");
            userInfo.setUserInfoVips(list);

                UserInfo userInfo1= new UserInfo();
                userInfo1.setName("Android");
                userInfo1.setAge("2");
                userInfo1.setLike("Kotlin");
                List<String> list1 = new ArrayList<>();
                list1.add("Java");
                list1.add("Kotlin");
                list1.add("Groovy");
                list1.add("Gradle");
                userInfo1.setUserInfoVips(list);
                List<UserInfo> list2 = new ArrayList<>();
                list2.add(userInfo);
                list2.add(userInfo1);
            DataBaseHelper.getInstance().getDataBaseUserInfoHelper().insertOrReplace(userInfo1);
                break;
            case R.id.thread_http:
                HanToast.show("我是查询");
                show();
//                List<UserInfo> userInfos = DataBaseHelper.getInstance().getDataBaseUserInfoHelper().queryUser(UserInfoDao.Properties.Age, "2");
//
//
//                if(userInfos == null){
//                   L.i("DataBase","我是空");
//               } else {
//
//                   for (int i = 0; i < userInfos.size(); i ++){
//                       UserInfo userInfo2 = userInfos.get(i);
//                       L.i("DataBase","我是"+userInfo2.getName() + "年龄"+userInfo2.getAge() + "我喜欢"+userInfo2.getLike() +"会员卡号");
//                       for (int m = 0; m < userInfo2.getUserInfoVips().size(); m ++){
//                           L.i("DataBase","会员卡号"+userInfo2.getUserInfoVips().get(m));
//
//                       }
//                   }
//               }
                break;
            case R.id.thread_work:
                HanToast.show("Config插入");
//                Student student = new Student();
//                student.setId(1);
//                student.setStudentName("yzh");
//                student.setStudentAge("2222");
//
//                boolean insert = DataBaseUtils.getStudentDao().insert(student);
//                if(insert){
//                    L.i("DataBaseDao","插入成功");
//                } else {
//                    L.i("DataBaseDao","插入失败");
//
//                }

//                UserConfig.getInstance().UserLike = "本兮";
//                UserConfig.getInstance().UserAge = "22";
//                UserConfig.getInstance().UserName = "YZH";
                List<String> vipLsit= new ArrayList<>();
                vipLsit.add("12343567889");
                vipLsit.add("adsfdghjytrew");
                vipLsit.add("123qwetryt43567889");
                vipLsit.add("ewrt");
                vipLsit.add("ertyuytrewsdvbn");
                UserConfig.getInstance().UserVipList = vipLsit;
                UserConfig.getInstance().commit();



//                com.han.hanmaxmin.common.greendao1.model.User user = new com.han.hanmaxmin.common.greendao1.model.User();
//                user.setAge("333");
//                user.setName("yhd");
//                boolean insert1 = DataBaseUtils.getUserDao().insert(user);
//                if(insert1){
//                    L.i("DataBaseDao","插入成功");
//                } else {
//                    L.i("DataBaseDao","插入失败");
//
//                }


                break;
            case R.id.thread_single:
                HanToast.show("Config查询");
//                Student student1 = DataBaseUtils.getStudentDao().queryValue(Student.class, StudentDao.Properties.StudentAge, "2222");

//                if(student1 == null){
//                    L.i("DataBaseDao", "我是空");
//                } else {
//                    L.i("DataBaseDao","Student.getStudentAge= " + student1.getStudentAge() + student1.getStudentName());
//                }
//
//
//                com.han.hanmaxmin.common.greendao1.model.User user1 = DataBaseUtils.getUserDao().queryValue(com.han.hanmaxmin.common.greendao1.model.User.class, UserDao.Properties.Age, "333");
//
//                if(user1 == null){
//                    L.i("DataBaseDao", "我是空");
//                } else {
//                    L.i("DataBaseDao","User= " + user1.getAge() + user1.getName());
//                }

                L.i("HanProperties",UserConfig.getInstance().UserName + UserConfig.getInstance().UserAge + UserConfig.getInstance().UserLike);

                List<String> userVipList = UserConfig.getInstance().UserVipList;
                for (String str : userVipList){
                    L.i("HanProperties","userVipList==" + str);
                }

                break;

            case R.id.tv_intent:
commitFragment(new DataBaseListFragment());
                break;


        }
    }



        @NeedPermission(value = {Manifest.permission.CALL_PHONE}, requestCode = 0)
        private void show (){
            HanToast.show("同意了");

        }

        @PermissionCancel
        private void cancelPermission(CancelBean cancelBean){
               HanToast.show("你取消了我的权限，你不能在活着");
        }

        @PermissionDenied
        private void denyPermission(DenyBean denyBean){
            HanToast.show("你拒绝了我的权限，你失去我了");

        }


    @Override
    public boolean isOpenViewState() {
        return true;
    }

    @Override
    public void loading(int resId) {

    }

    @Override
    public void loading(int resId, boolean cancelAble) {

    }

}
