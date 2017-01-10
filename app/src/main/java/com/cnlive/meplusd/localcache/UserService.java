package com.cnlive.meplusd.localcache;

import android.content.Context;
import android.content.SharedPreferences;
import com.cnlive.meplusd.model.User;


/**
 * Created by zhangshuai on 2017-01-06.
 *
 * 缓存用户个人信息
 */

public class UserService {

    private final String shared_name="MePlus_demo";

    private static UserService mUserService;

    private Context mContext;

    private SharedPreferences mSharedPreferences;

    public UserService(Context context) {
        this.mContext = context;
        if (mSharedPreferences == null ){
            mSharedPreferences=context.getSharedPreferences(shared_name, Context.MODE_PRIVATE);
        }
    }

    private final String KEY_ISLOGIN="isLogin";  //是否登录
    private final String KEY_ACCOUNT="account";  //用户名

    //更新、保存用户信息
    public void updateUserInfo(User oldUser, User newUser){
        clserUserInfo(oldUser);
        setUserInfo(newUser);
    }

    //保存用户信息
    private void setUserInfo(User user){
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        if (user!=null){
            editor.putBoolean(KEY_ISLOGIN,true);
            editor.putString(KEY_ACCOUNT,user.getName());
        }
        editor.commit();
    }

    //获取用户信息
    public User getUserInfo(){
        User user=new User();
        user.setName(mSharedPreferences.getString(KEY_ACCOUNT,""));
        return user;
    }

    //清空缓存信息
    public void clserUserInfo(User user){
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        if (user != null){
            editor.clear();
            editor.commit();
        }
    }

    //设置 是否自动登录
    public void AutoLogin(boolean autologin){
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putBoolean(KEY_ISLOGIN,autologin);
        editor.commit();
    }

    //判断是否登录
    public boolean isLogin(){
        boolean login=mSharedPreferences.getBoolean(KEY_ISLOGIN,false);
        return login;
    }


}
