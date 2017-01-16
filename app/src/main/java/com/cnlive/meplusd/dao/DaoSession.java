package com.cnlive.meplusd.dao;

import android.database.sqlite.SQLiteDatabase;

import com.cnlive.meplusd.model.AboutModel;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by zhangshuai on 2017-01-16.
 */

public class DaoSession extends AbstractDaoSession {

    //关于
    private final DaoConfig aboutDaoConfig;
    private final AboutDao mAboutDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?,?>>,DaoConfig> daoConfigMap) {
        super(db);

        //关于
        aboutDaoConfig=daoConfigMap.get(AboutDao.class).clone();
        aboutDaoConfig.initIdentityScope(type);
        mAboutDao=new AboutDao(aboutDaoConfig,this);
        registerDao(AboutModel.class,mAboutDao);


    }

    public void clear(){
        aboutDaoConfig.getIdentityScope().clear();
    }


    public AboutDao getAboutDao(){ return mAboutDao; }

}
