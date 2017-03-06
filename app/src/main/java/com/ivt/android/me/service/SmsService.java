package com.ivt.android.me.service;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.ivt.android.me.api.SmsInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class SmsService extends ContentObserver {

    private Cursor cursor = null;
    private Context context;

    public SmsInterface smsInterface;

    public void setSmsMessage(SmsInterface sms){ this.smsInterface=sms; }

    public SmsService(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }



    @Override
    public void onChange(boolean selfChange) {

        super.onChange(selfChange);

        Log.i("SMSTest","Begin");

        //读取收件箱中指定号码的短信
//        cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), new String[]{"_id", "address", "read", "body"},
//                " address=? and read=?", new String[]{"10086", "0"}, "_id desc");//按id排序，如果按date排序的话，修改手机时间后，读取的短信就不准了

        cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), new String[]{"_id", "address", "read", "body"},
                null, null, "_id desc");

        Log.i("SMSTest","cursor.isBeforeFirst(): " + cursor.isBeforeFirst() + " cursor.getCount():  " + cursor.getCount());
        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            int smsbodyColumn = cursor.getColumnIndex("body");
            String smsBody = cursor.getString(smsbodyColumn);
            Log.i("SMSTest","smsBody = " + smsBody);

            //LoginActivity.mEtPhonewCode.setText(getDynamicPassword(smsBody));  //需要填写验证码的控件框
            smsInterface.getSmsMessage(getDynamicPassword(smsBody));
        }

        //在用managedQuery的时候，不能主动调用close()方法， 否则在Android 4.0+的系统上， 会发生崩溃
        if(Build.VERSION.SDK_INT < 14) {
            cursor.close();
        }
    }

    public static String getDynamicPassword(String str) {
        Pattern continuousNumberPattern = Pattern.compile("[0-9\\.]+");
        Matcher m = continuousNumberPattern.matcher(str);
        String dynamicPassword = "";
        while(m.find()){
            if(m.group().length() == 6) {
                System.out.print(m.group());
                dynamicPassword = m.group();
            }
        }

        return dynamicPassword;
    }

}
