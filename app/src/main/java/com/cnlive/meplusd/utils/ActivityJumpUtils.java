package com.cnlive.meplusd.utils;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by zhangshuai on 2017-01-11.
 */

public class ActivityJumpUtils {

    /*普通跳转*/
    public static void JumpCommonActivity(Activity now_activity, Class cla){
        now_activity.finish();
        ActivityManageUtil.getInstance().finishActivity(now_activity);
        Intent intent=new Intent();
        intent.putExtra("name","admin");
        intent.setClass(now_activity,cla);
        now_activity.startActivity(intent);
    }
}
