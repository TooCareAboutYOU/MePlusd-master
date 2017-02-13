package com.ivt.android.me.api;

import com.ivt.android.me.model.ErrorMessage;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by zhangshuai on 2017-01-10.
 */

public interface UserAPI {

    /*
    * 登录成 同步用户信息
    *
    * */
    @GET("/zGWUserSynchronization.html")
    void SynchronousUserData(@Query("version") String versionNum,
                             @Query("plat") String plat,
                             @Query("data") String data,
                             Callback<ErrorMessage> callback);




}
