package com.ivt.android.me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ivt.android.me.R;
import com.ivt.android.me.ui.base.BaseActivity;
import com.ivt.android.me.utils.ActivityJumpUtils;


public class SplashActivity extends BaseActivity implements View.OnClickListener {

    SimpleDraweeView mSimpleImage1;
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //mSimpleImage1.setImageResource(R.drawable.splash);
        //mSimpleImage1.setOnClickListener(this);
        //延时3秒跳转到首页
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //startActivity(new Intent(SplashActivity.this, MainActivity.class));
                //finish();
                timer.start();
            }
        }, 1000);

        mSimpleImage1= (SimpleDraweeView) findViewById(R.id.simpleImage1);
        tvTime= (TextView) findViewById(R.id.tv_time);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    /*验证码倒计时*/
    public CountDownTimer timer = new CountDownTimer(5000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            String str=String.format("%d s后跳转",(millisUntilFinished / 1000));
            tvTime.setText(str);
        }

        @Override
        public void onFinish() {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    };

    @Override
    public void onClick(View view) {
        back();
        ActivityJumpUtils.JumpActivity(SplashActivity.this, SearchActivity.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //调用双击退出函数
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            timer.cancel();
            exitBy2Click();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        timer.onFinish();
    }
}
