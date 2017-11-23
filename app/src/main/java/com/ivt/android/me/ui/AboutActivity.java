package com.ivt.android.me.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ivt.android.me.R;
import com.ivt.android.me.localcache.AboutCache;
import com.ivt.android.me.model.AboutModel;
import com.ivt.android.me.model.ExAboutModel;
import com.ivt.android.me.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends BaseActivity {

    EditText mEtId;
    EditText mEtMsg;
    EditText mEtCode;
    EditText mEtMark;
    EditText mEtExtral;
    Button mBtnSave;
    Button mBtnRead;
    TextView mTvInfo;

    private ExAboutModel exAboutModel;
    private List<AboutModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exAboutModel = new ExAboutModel(
                        mEtId.getText().toString(),
                        mEtMsg.getText().toString(),
                        mEtCode.getText().toString(),
                        mEtMark.getText().toString(),
                        mEtExtral.getText().toString()
                );
                AboutCache.addAboutModel(AboutActivity.this, exAboutModel);
            }
        });
        mBtnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = new ArrayList<AboutModel>();
                list.addAll(AboutCache.getAboutModel(AboutActivity.this));
                mTvInfo.setText(list.toString());
            }
        });


    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mEtId= (EditText) findViewById(R.id.et_id);
        mEtMsg= (EditText) findViewById(R.id.et_msg);
        mEtCode= (EditText) findViewById(R.id.et_code);
        mEtMark= (EditText) findViewById(R.id.et_mark);
        mEtExtral= (EditText) findViewById(R.id.et_extral);
        mBtnSave= (Button) findViewById(R.id.btn_save);
        mBtnRead= (Button) findViewById(R.id.btn_read);
        mTvInfo= (TextView) findViewById(R.id.tv_info);
    }
}
