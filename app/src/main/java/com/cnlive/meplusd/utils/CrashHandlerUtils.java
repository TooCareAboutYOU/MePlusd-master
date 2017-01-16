package com.cnlive.meplusd.utils;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import com.cnlive.meplusd.ui.SplashActivity;

/**
 * Created by zhangshuai on 2017-01-11.
 * 捕获异常并在应用崩溃后重启应用
 */

public class CrashHandlerUtils implements Thread.UncaughtExceptionHandler {

    public static final String TAG="CrashHandlerUtils";

    private static CrashHandlerUtils instance=new CrashHandlerUtils();

    private Context mContext;

    public CrashHandlerUtils() { }

    public static CrashHandlerUtils getInstance(){ return instance; }


    public void init(Context context){
        this.mContext=context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.d(TAG, "uncaughtException");

        throwable.printStackTrace();

        new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                new AlertDialog.Builder(mContext)
                        .setTitle("提示")
                        .setCancelable(false)
                        .setMessage("蜜家已停止运行,是否重新启动？")
                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(mContext.getApplicationContext(), SplashActivity.class);
                                PendingIntent restartIntent=PendingIntent.getActivity(mContext.getApplicationContext(),
                                        0,intent,Intent.FLAG_ACTIVITY_NEW_TASK);
                                //退出程序
                                AlarmManager mgr=(AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
                                //1秒钟后重启应用
                                mgr.set(AlarmManager.RTC,System.currentTimeMillis()+1000,restartIntent);
                                Process.killProcess(Process.myPid());
                            }
                        })
                        .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                System.exit(0);
                            }
                        }).create().show();
                Looper.loop();
            }
        }.start();

    }
}
