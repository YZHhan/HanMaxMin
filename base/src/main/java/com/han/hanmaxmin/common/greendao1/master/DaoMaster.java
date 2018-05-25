package com.han.hanmaxmin.common.greendao1.master;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.han.hanmaxmin.common.greendao1.model.Student;
import com.han.hanmaxmin.common.greendao1.model.StudentDao;
import com.han.hanmaxmin.common.greendao1.model.UserDao;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

public class DaoMaster extends AbstractDaoMaster {

    public static final int SCHEMA_VERSION = 15;

    public DaoMaster(Database db, int schemaVersion) {
        super(db, schemaVersion);
    }

    @Override
    public AbstractDaoSession newSession() {
        return null;
    }

    @Override
    public AbstractDaoSession newSession(IdentityScopeType type) {
        return null;
    }

    public static void createAllTables(Database db, boolean ifNotExists) {
        StudentDao.createTable(db, ifNotExists);
        UserDao.createTable(db, ifNotExists);
    }

    public static void dropAllTables(Database db, boolean ifExists) {
        StudentDao.dropTable(db, ifExists);
        UserDao.dropTable(db, ifExists);
    }

    public static abstract class OpenHelper extends DatabaseOpenHelper{

        public OpenHelper(Context context, String name, int version) {
            super(context, name, version);
        }

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }

    }

    public static class DevOpenHelper extends OpenHelper{

        public DevOpenHelper(Context context, String name, int version) {
            super(context, name, version);
        }

        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }
}
