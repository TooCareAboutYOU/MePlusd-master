package com.cnlive.meplusd.utils;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by zhangshuai on 2017-01-11.
 */

public class ActivityJumpUtils {

    /*普通跳转1*/
    public static void JumpComUnFinishActivity(Activity now_activity, Class cla){
        Intent intent=new Intent();
        intent.setClass(now_activity,cla);
        now_activity.startActivity(intent);
    }

    /*普通跳转2：从栈顶移除当前的activity活动，再跳转*/
    public static void JumpComFinishActivity(Activity now_activity, Class cla){
        ActivityManageUtil.getInstance().finishActivity(now_activity);
        now_activity.finish();
        now_activity.startActivity(new Intent(now_activity,cla));
    }
}
