package com.cnlive.meplusd.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.cnlive.meplusd.R;
import com.cnlive.meplusd.ui.base.BaseActivity;
import com.cnlive.meplusd.utils.ActivityManageUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;

import static com.cnlive.meplusd.R.id.simpleImage1;

public class SplashActivity extends BaseActivity {

    @Bind(simpleImage1)
    SimpleDraweeView mSimpleImage1;
    @Bind(R.id.tv_info)
    TextView mTvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


    }

    @Override
    protected void AddActivityToTask() {
        ActivityManageUtil.getInstance().pushToStatic(SplashActivity.class);
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        //调用双击退出函数
//        if(keyCode == KeyEvent.KEYCODE_BACK) { exitBy2Click(); }
//        return false;
//    }
}
