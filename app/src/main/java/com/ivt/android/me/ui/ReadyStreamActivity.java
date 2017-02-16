package com.ivt.android.me.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ivt.android.me.R;
import com.ivt.android.me.ui.base.BaseActivity;

import org.xutils.view.annotation.Event;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReadyStreamActivity extends BaseActivity {

    @Bind(R.id.img_slc)
    ImageView mImgSlc;

    ArrayList<String> pathList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_stream);
        ButterKnife.bind(this);
    }

    @Event(value = R.id.btn_selector, type = View.OnClickListener.class)
    private void getPic(View v) {
        Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
    }


}
