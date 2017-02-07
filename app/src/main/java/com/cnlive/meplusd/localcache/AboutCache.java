package com.cnlive.meplusd.localcache;

import android.content.Context;

import com.cnlive.meplusd.dao.AboutDao;
import com.cnlive.meplusd.model.AboutModel;
import com.cnlive.meplusd.dao.GreenDaoHelper;
import com.cnlive.meplusd.model.ExAboutModel;

import java.util.List;

/**
 * Created by zhangshuai on 2017-01-16.
 */

public class AboutCache {

    public static boolean checkAboutModel(Context context,String id){
        GreenDaoHelper daoHelper=GreenDaoHelper.getInstance(context);
        return daoHelper.getAboutDao().queryBuilder().where(AboutDao.Properties.Id.eq(id)).count()>0;
    }

    public static void addAboutModel(Context context, ExAboutModel item){
        AboutDao aboutDao=GreenDaoHelper.getInstance(context).getAboutDao();
        AboutModel aboutModel=new AboutModel(
                item.getId(),
                item.getMsg(),
                item.getCode(),
                item.getMark(),
                item.getExtral());
        aboutDao.insert(aboutModel);
    }

    //精确查询
    public static AboutModel getAllAboutModel(Context context,String id){
        GreenDaoHelper daoHelper=GreenDaoHelper.getInstance(context);
        List<AboutModel> list=daoHelper.getAboutDao().queryBuilder().where(AboutDao.Properties.Id.eq(id)).list();
        if (list != null && list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

    public static void deleteAboutModel(Context context,String id){
        GreenDaoHelper.getInstance(context).getAboutDao().deleteByKey(id);
    }

    public static List<AboutModel> getAboutModel(Context context){
        return GreenDaoHelper.getInstance(context).getAboutDao().queryBuilder().list();
    }

}
