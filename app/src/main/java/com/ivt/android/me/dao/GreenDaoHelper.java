package com.ivt.android.me.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zhangshuai on 2017-01-16.
 */

public class GreenDaoHelper {

    private static GreenDaoHelper instance;
    private DaoSession mDaoSession;

    public GreenDaoHelper(Context context) {
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(context,"meplus-db",null);
        SQLiteDatabase db= helper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(db);
        mDaoSession= (DaoSession) daoMaster.newSession();
    }

    public static synchronized GreenDaoHelper getInstance(Context context){
        if (instance == null){
            instance=new GreenDaoHelper(context.getApplicationContext());
        }
        return instance;
    }

    //关于
    public AboutDao getAboutDao(){ return mDaoSession.getAboutDao(); }
}
