package com.cnlive.meplusd.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.cnlive.meplusd.R;
import com.cnlive.meplusd.ui.base.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;

import static com.cnlive.meplusd.R.id.simpleImage1;

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    @Bind(simpleImage1)
    SimpleDraweeView mSimpleImage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSimpleImage1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.finish();
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //调用双击退出函数
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }
}
