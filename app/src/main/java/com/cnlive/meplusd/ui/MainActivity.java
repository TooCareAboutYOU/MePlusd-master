package com.cnlive.meplusd.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.cnlive.meplusd.R;
import com.cnlive.meplusd.ui.base.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;

import static com.cnlive.meplusd.R.id.simpleImage1;

public class MainActivity extends BaseActivity {

    @Bind(simpleImage1)
    SimpleDraweeView mSimpleImage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSimpleImage1.setImageURI(Uri.parse("http://yweb3.cnliveimg.com/img/cnlive/161121103649189_625.png"));
        mSimpleImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
            }
        });
    }


    @Override
    protected void BackPressed() { }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //调用双击退出函数
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }
}
