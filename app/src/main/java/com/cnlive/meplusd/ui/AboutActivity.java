package com.cnlive.meplusd.ui;

import android.os.Bundle;

import com.cnlive.meplusd.R;
import com.cnlive.meplusd.ui.base.BaseActivity;
import com.cnlive.meplusd.utils.ActivityJumpUtils;
import com.cnlive.meplusd.utils.ActivityManageUtil;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityJumpUtils.JumpComFinishActivity(AboutActivity.this,MainActivity.class);
    }


    @Override
    protected void AddActivityToTask() {
        ActivityManageUtil.getInstance().pushToStatic(AboutActivity.class);
    }
}
