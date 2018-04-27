package com.han.hanmaxmin.common.greendao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.han.hanmaxmin.common.greendao.model.DaoMaster;
import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.common.greendao.model.UserInfoDao;
import com.han.hanmaxmin.common.log.L;

import org.greenrobot.greendao.database.Database;

/**
 * Created by ptxy on 2018/3/22.
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        L.i("HanMaxMin","数据库升级的时候");
        MigrationHelper.getInstance().migrate(db, UserInfoDao.class);
    }

}
