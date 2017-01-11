package com.cnlive.meplusd.application;

import android.app.Application;

import com.cnlive.meplusd.utils.DeviceUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by zhangshuai on 2017-01-10.
 */

public class MePlusApplication extends Application {

    public static MePlusApplication mMePlusApplication;

    public static String ime="";

    @Override
    public void onCreate() {
        super.onCreate();

        mMePlusApplication=this;
        ImagePipelineConfig config= OkHttpImagePipelineConfigFactory.newBuilder(this,new OkHttpClient()).setDownsampleEnabled(true).build();
        Fresco.initialize(this,config);
        ime= DeviceUtils.getIEMI(this);
    }

    public static synchronized MePlusApplication getInstance() { return mMePlusApplication; }

}
