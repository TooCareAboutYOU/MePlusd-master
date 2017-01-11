package com.cnlive.meplusd.ui.base;

import android.app.Activity;
import android.os.Bundle;

import com.cnlive.meplusd.utils.CrashHandlerUtils;

import butterknife.ButterKnife;

public class BaseActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);


        DealWithException();
    }


    private void DealWithException(){
        CrashHandlerUtils crashHandlerUtils=CrashHandlerUtils.getInstance();
        crashHandlerUtils.init(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
