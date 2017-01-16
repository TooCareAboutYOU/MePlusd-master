package com.cnlive.meplusd.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.cnlive.meplusd.model.AboutModel;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by zhangshuai on 2017-01-16.
 */

public class AboutDao extends AbstractDao<AboutModel,String> {

    public static final String TABLENAME="ABOUTMODEL";

    // Properties of entity AboutModel
    // Can be used for QueryBuilder and for referencing column names.

    public static class Properties{
        public final static Property Id=new Property(0,String.class,"id",true,"ID");
        public final static Property Msg=new Property(1,String.class,"mag",false,"MSG");
        public final static Property Code=new Property(2,String.class,"code",false,"CODE");
        public final static Property Mark=new Property(3,String.class,"mark",false,"MARK");
        public final static Property Extral=new Property(4,String.class,"extral",false,"EXTRAL");
    }


    public AboutDao(DaoConfig config) { super(config); }

    public AboutDao(DaoConfig config, AbstractDaoSession daoSession) { super(config, daoSession); }

    public static void createTable(SQLiteDatabase db,boolean ifNotExists){
        String contraint=ifNotExists ? "IF NOT EXISTS" : "";
        db.execSQL("CREATE TABLE " + contraint + "'ABOUTMODEL'("+
                "'ID' TEXT PRIMARY KEY NOT NULL ," +
                "'MSG' TEXT ," +
                "'CODE' TEXT ," +
                "'MARK' TEXT ," +
                "'EXTRAL' TEXT );");
    }

    public static void dropTable(SQLiteDatabase db ,boolean ifExists){
        String sql="DROP TABLE "+ (ifExists ? "IF EXISTS " : "") + "'ABOUTMODEL'";
        db.execSQL(sql);
    }


    @Override
    protected AboutModel readEntity(Cursor cursor, int offset) {
        AboutModel entity=new AboutModel(
                cursor.isNull(offset+0) ? null : cursor.getString(offset + 0),
                cursor.isNull(offset+1) ? null : cursor.getString(offset + 1),
                cursor.isNull(offset+2) ? null : cursor.getString(offset + 2),
                cursor.isNull(offset+3) ? null : cursor.getString(offset + 3),
                cursor.isNull(offset+4) ? null : cursor.getString(offset + 4)
        );
        return entity;
    }

    @Override
    protected String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset+0) ? null : cursor.getString(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, AboutModel entity, int offset) {
        entity.setId(cursor.isNull(offset+0) ? null : cursor.getString(offset + 0));
        entity.setMsg(cursor.isNull(offset+1) ? null : cursor.getString(offset + 1));
        entity.setCode(cursor.isNull(offset+2) ? null : cursor.getString(offset + 2));
        entity.setMark(cursor.isNull(offset+3) ? null : cursor.getString(offset + 3));
        entity.setExtral(cursor.isNull(offset+4) ? null : cursor.getString(offset + 4));
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, AboutModel entity) {
        stmt.clearBindings();

        String id=entity.getId();  if (id != null){ stmt.bindString(1,id); }
        String msg=entity.getMsg();  if (msg != null){ stmt.bindString(2,msg); }
        String code=entity.getCode();  if (code != null){ stmt.bindString(3,code); }
        String mark=entity.getMark();  if (mark != null){ stmt.bindString(4,mark); }
        String extral=entity.getExtral();  if (extral != null){ stmt.bindString(5,extral); }
    }

    @Override
    protected String updateKeyAfterInsert(AboutModel entity, long rowId) { return entity.getId(); }

    @Override
    protected String getKey(AboutModel entity) {
        if (entity != null){
            return entity.getId();
        }else {
            return null;
        }
    }

    @Override
    protected boolean isEntityUpdateable() { return true; }
}
