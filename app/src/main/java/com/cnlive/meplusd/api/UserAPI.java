package com.cnlive.meplusd.api;

import com.cnlive.meplusd.model.ErrorMessage;

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
