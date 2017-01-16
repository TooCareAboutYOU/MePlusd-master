package com.cnlive.meplusd.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;

/**
 * Created by zhangshuai on 2017-01-16.
 */

public class DaoMaster extends AbstractDaoMaster {

    public static int SCHEMA_VERSION=1003;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db,boolean ifNotExists){
        AboutDao.createTable(db,ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTabls(SQLiteDatabase db,boolean ifExists){
        AboutDao.dropTable(db,ifExists);
    }

    public static abstract class OpenHelper extends SQLiteOpenHelper{

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        //Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
        @Override
        public void onCreate(SQLiteDatabase db) { createAllTables(db,false); }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper{

        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        //Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            dropAllTabls(db,true);
            onCreate(db);
        }
    }


//
//    public DaoMaster(SQLiteDatabase db, int schemaVersion) {
//        super(db, schemaVersion);
//        this.SCHEMA_VERSION=schemaVersion;
//        registerDaoClass(AboutModel.class);
//    }


    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(AboutDao.class);
    }

    @Override
    public AbstractDaoSession newSession() {
        return new DaoSession(db,IdentityScopeType.Session,daoConfigMap);
    }

    @Override
    public AbstractDaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db,type,daoConfigMap);
    }

}
