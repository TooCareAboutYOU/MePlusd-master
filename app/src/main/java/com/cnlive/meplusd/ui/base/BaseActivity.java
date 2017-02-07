package com.cnlive.meplusd.ui.base;

import android.app.Activity;
import android.os.Bundle;

import com.cnlive.meplusd.utils.ActivityManageUtil;
import com.cnlive.meplusd.utils.CrashHandlerUtils;
import com.cnlive.meplusd.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public abstract class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DealWithException();
        //AddActivityToTask();
        ActivityManageUtil.getInstance().pushToStatic(BaseActivity.class);
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

    //protected abstract void AddActivityToTask();

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
            ToastUtils.CustomToast(this,"再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            ActivityManageUtil.getInstance().finishAllActivity();
            finish();
            System.exit(0);
        }
    }
}
