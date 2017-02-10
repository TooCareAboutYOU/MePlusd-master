package com.ivt.android.me.api;

import com.ivt.android.me.model.ErrorMessage;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by zhangshuai on 2017-01-10.
 */

public interface UserAPI {

    /*
    * 测试
    *
    * */
    @GET("/login.html")
    void getInfo(Callback<ErrorMessage> callback);
    //http://120.131.13.185:8080/goldenline-portal-clt/vote.html?voteId=1

}
