package com.ivt.android.me.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.ivt.android.me.utils.DeviceUtils;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by zhangshuai on 2017-01-10.
 */

public class MePlusApplication extends Application {

    public static MePlusApplication mMePlusApplication;

    public static String ime="";

    //视讯云SDK
    public String app_secret = "33eb04e9ca7001e878eb8a57e2a1068d7b48c2909fc341";

    @Override
    public void onCreate() {
        super.onCreate();

        //Config.init(this, "118_itdr6ijv09", "24557a1060598e010749ec11b43ac9d62e8a765d3463cf");


        mMePlusApplication=this;
        ImagePipelineConfig config= OkHttpImagePipelineConfigFactory.newBuilder(this,new OkHttpClient()).setDownsampleEnabled(true).build();
        Fresco.initialize(this,config);
        ime= DeviceUtils.getIEMI(this);
    }

    public static synchronized MePlusApplication getInstance() { return mMePlusApplication; }

}
