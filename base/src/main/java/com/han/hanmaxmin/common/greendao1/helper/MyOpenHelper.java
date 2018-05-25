package com.han.hanmaxmin.common.greendao1.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.han.hanmaxmin.common.greendao.helper.MigrationHelper;
import com.han.hanmaxmin.common.greendao.model.UserInfoDao;
import com.han.hanmaxmin.common.greendao1.master.DaoMaster;
import com.han.hanmaxmin.common.greendao1.model.StudentDao;
import com.han.hanmaxmin.common.greendao1.model.UserDao;

import org.greenrobot.greendao.database.Database;

public class MyOpenHelper extends com.han.hanmaxmin.common.greendao.model.DaoMaster.DevOpenHelper{

    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.getInstance().migrate(db, StudentDao.class);
        MigrationHelper.getInstance().migrate(db, UserDao.class);

    }
}
