package com.ivt.android.me.utils;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by zhangshuai on 2017-01-11.
 */

public class ActivityJumpUtils {

    /*普通跳转1*/
    public static void JumpActivity(Activity now_activity, Class cla){
        Intent intent=new Intent();
        intent.setClass(now_activity,cla);
        now_activity.startActivity(intent);
    }
}
