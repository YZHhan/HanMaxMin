package com.han.hanmaxmin.common.greendao1.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.han.hanmaxmin.common.greendao.helper.MyOpenHelper;
import com.han.hanmaxmin.common.greendao.model.DaoMaster;
import com.han.hanmaxmin.common.greendao.model.DaoSession;
import com.han.hanmaxmin.common.greendao.model.GreenDaoConstants;
import com.han.hanmaxmin.common.utils.HanHelper;

/**
 * 对数据库做封装操作
 */
public class DataBaseHelper {
    private static DataBaseHelper helper;
    private static DaoMaster master;
    private static DaoSession session;
    private static DaoMaster.DevOpenHelper devOpenHelper;
    private static final String  DATA_NAME = "grapecollpge";

    private DataBaseHelper() {
       init(HanHelper.getInstance().getApplication());

    }

    /**
     * 初始化数据库
     */
    public void init(Context context) {
        if (null ==session) {
            synchronized (DataBaseHelper.class) {
                if (null ==session) {
                    devOpenHelper = new DaoMaster.DevOpenHelper(context, DATA_NAME);
                    getDaoMaster(context);
                    getDaoSession(context);
                }
            }
        }
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
     * 获取DaoMaster
     * 判断是否存在数据库，如果没有就创建数据库
     */
    public static DaoMaster getDaoMaster(Context context){
        if(null == master){
            synchronized (DataBaseHelper.class){
                if(null == master){
                    com.han.hanmaxmin.common.greendao1.helper.MyOpenHelper openHelper = new com.han.hanmaxmin.common.greendao1.helper.MyOpenHelper(context, DATA_NAME , null);
                    master = new DaoMaster(openHelper.getWritableDatabase());
                }
            }
        }
        return master;
    }

    public static DaoSession getDaoSession(Context context){
        if(null == session){
            synchronized (DataBaseHelper.class){
                session = getDaoMaster(context).newSession();
            }
        }
        return session;
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


    /**
     * 关闭数据库
     */
    public void closeDataBase(){
        closeHelper();
        closeDaoSession();
    }

    public void closeDaoSession(){
        if (null != session){
            session.clear();
            session = null;
        }
    }

    public  void  closeHelper(){
        if (devOpenHelper!=null){
            devOpenHelper.close();
            devOpenHelper = null;
        }
    }





}
