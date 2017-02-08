package com.cnlive.meplusd.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cnlive.meplusd.R;
import com.cnlive.meplusd.localcache.AboutCache;
import com.cnlive.meplusd.model.AboutModel;
import com.cnlive.meplusd.model.ExAboutModel;
import com.cnlive.meplusd.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class AboutActivity extends BaseActivity {

    @Bind(R.id.et_id)
    EditText mEtId;
    @Bind(R.id.et_msg)
    EditText mEtMsg;
    @Bind(R.id.et_code)
    EditText mEtCode;
    @Bind(R.id.et_mark)
    EditText mEtMark;
    @Bind(R.id.et_extral)
    EditText mEtExtral;
    @Bind(R.id.btn_save)
    Button mBtnSave;
    @Bind(R.id.btn_read)
    Button mBtnRead;
    @Bind(R.id.tv_info)
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

}
