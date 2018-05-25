package com.han.hanmaxmin.common.greendao1.utils;

import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.common.greendao.model.UserInfoDao;
import com.han.hanmaxmin.common.greendao1.dao.StudentBaseDao;
import com.han.hanmaxmin.common.greendao1.dao.UserBaseDao;
import com.han.hanmaxmin.common.greendao1.model.UserDao;
import com.han.hanmaxmin.common.utils.HanHelper;

/**
 * DataBase的帮助类
 */
public class DataBaseUtils {
    private static StudentBaseDao studentBaseDao = new StudentBaseDao(HanHelper.getInstance().getApplication());;
    private static UserBaseDao userInfoDao = new UserBaseDao(HanHelper.getInstance().getApplication());

    public static StudentBaseDao getStudentDao(){
        return  studentBaseDao;
    }

    public static UserBaseDao getUserDao(){
        return userInfoDao;
    }



}
