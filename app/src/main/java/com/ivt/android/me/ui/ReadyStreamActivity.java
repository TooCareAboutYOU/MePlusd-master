package com.ivt.android.me.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ivt.android.me.R;
import com.ivt.android.me.ui.base.BaseActivity;

import org.xutils.view.annotation.Event;

import java.util.ArrayList;

public class ReadyStreamActivity extends BaseActivity {

    ImageView mImgSlc;

    ArrayList<String> pathList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_stream);
        mImgSlc= (ImageView) findViewById(R.id.img_slc);
        findViewById(R.id.btn_selector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ReadyStreamActivity.this, "点击了", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
