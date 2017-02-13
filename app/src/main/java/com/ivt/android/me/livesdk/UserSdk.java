package com.ivt.android.me.livesdk;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.cnlive.libs.user.model.UserData;

/**
 * Created by zhangshuai on 2017-02-13.
 */

public class UserSdk {

    private  static Context mContext;

    public UserSdk(Context context) { mContext = context; }

    public static String LoginService(UserData mUserData){

        JSONObject obj=new JSONObject();
        obj.put("uid",mUserData.getData().getUid());
        obj.put("nickName",mUserData.getData().getUid());
        obj.put("gender",mUserData.getData().getUid());
        obj.put("location",mUserData.getData().getUid());
        obj.put("faceUrl",mUserData.getData().getUid());
        obj.put("mobile",mUserData.getData().getMobile());
        obj.put("email",mUserData.getData().getEmail());
        obj.put("extInfo",mUserData.getData().getExtInfo());
        obj.put("qqUid",mUserData.getData().getQqUid());
        obj.put("wxUid",mUserData.getData().getWxUid());
        obj.put("sinaUid",mUserData.getData().getSinaUid());
        obj.put("renrenUid",mUserData.getData().getRenrenUid());
        obj.put("hUid",mUserData.getData().gethUid());

        String str=obj.toString();

        return str;
    }
}
