package com.ivt.android.me.api;

import com.ivt.android.me.model.T1;
import com.ivt.android.me.model.TestData;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by zhangshuai on 2017-01-10.
 */

public interface CmsAPI {

    /*
    *
    * 首页 json
    * http://ad.wodpy.com:81/meplusd/hotVideoHome.html
    *
    * https://api.github.com/users/Guolei1130
    * */

    @GET("/Guolei1130")
    void getMainPageJson(Callback<TestData> callback);

}
