package com.cnlive.meplusd.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.cnlive.meplusd.utils.ActivityManageUtil;
import com.cnlive.meplusd.utils.CrashHandlerUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public abstract class BaseActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DealWithException();
        AddActivityToTask();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }


    private void DealWithException(){
        CrashHandlerUtils crashHandlerUtils=CrashHandlerUtils.getInstance();
        crashHandlerUtils.init(this);
    }

    protected abstract void AddActivityToTask();

    public void ExitActivityToTask(){ ActivityManageUtil.getInstance().finishActivity(this); }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    public void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            ActivityManageUtil.getInstance().finishAllActivity();
            System.exit(0);
        }
    }
}
