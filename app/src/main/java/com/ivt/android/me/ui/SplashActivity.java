package com.ivt.android.me.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ivt.android.me.R;
import com.ivt.android.me.ui.base.BaseActivity;
import com.ivt.android.me.utils.ActivityJumpUtils;

import butterknife.Bind;

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.simpleImage1)
    SimpleDraweeView mSimpleImage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //mSimpleImage1.setImageResource(R.drawable.splash);
        mSimpleImage1.setOnClickListener(this);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                finish();
//            }
//        }, 3000);
    }



    @Override
    public void onClick(View view) {
        back();
        ActivityJumpUtils.JumpActivity(SplashActivity.this, LoginActivity.class);
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
