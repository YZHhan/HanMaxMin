package com.han.hanmaxmin.common.greendao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.han.hanmaxmin.common.greendao.model.DaoMaster;
import com.han.hanmaxmin.common.greendao.model.DaoSession;
import com.han.hanmaxmin.common.greendao.model.GreenDaoConstants;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.mvp.fragment.HanHeaderViewPagerFragment;

import org.greenrobot.greendao.database.Database;

/**
 * Created by ptxy on 2018/3/21.
 * 数据库  MigrationHelper
 */

public class DataBaseHelper {

    private static DataBaseHelper helper;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static DaoMaster.DevOpenHelper devOpenHelper;

    private DataBaseHelper() {
        init(HanHelper.getInstance().getApplication());
    }

    public static DataBaseHelper getInstance(){
        if(helper == null){
            synchronized (DataBaseHelper.class){
                if(helper == null){
                    helper = new DataBaseHelper();
                }
            }
        }
        return helper;
    }


    /**
     * 初始化数据库
     */
    public void init(Context context){
        if(daoSession == null){
            synchronized (DataBaseHelper.class){
                if(daoSession == null){
                    // 初始化数据库信息。
                    devOpenHelper = new DaoMaster.DevOpenHelper(context, GreenDaoConstants.DATA_BASE_NAME);
                   getDaoMaster(context);
                    getDaoSession(context);
                }
            }
        }
    }

    /**
     * 获取DaoMaster
     * 判断是否存在数据库，如果没有就创建数据库
     */
    public static DaoMaster getDaoMaster(Context context){
        if(null == daoMaster){
            synchronized (DataBaseHelper.class){
                if(null == daoMaster){
                        MyOpenHelper openHelper = new MyOpenHelper(context, GreenDaoConstants.DATA_BASE_NAME , null);
                        daoMaster = new DaoMaster(openHelper.getWritableDatabase());
                }
            }
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context){
        if(null == daoSession){
            synchronized (DataBaseHelper.class){
                daoSession = getDaoMaster(context).newSession();
            }
        }
        return daoSession;
    }

    /**
     * 获取可读数据库
     * @param context
     * @return
     */
    public static SQLiteDatabase getReadableDatabase(Context context) {
        if (null == devOpenHelper) {
            getInstance();
        }
        return devOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     */
    public static SQLiteDatabase getWritableDatabase(Context context) {
        if (null == devOpenHelper) {
            getInstance();
        }
        return devOpenHelper.getWritableDatabase();
    }


    public DataBaseUserInfoHelper getDataBaseUserInfoHelper(){
        return DataBaseUserInfoHelper.getInstance();
    }
}
