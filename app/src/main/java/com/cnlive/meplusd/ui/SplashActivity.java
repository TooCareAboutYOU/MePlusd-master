package com.cnlive.meplusd.ui;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cnlive.meplusd.Config;
import com.cnlive.meplusd.R;
import com.cnlive.meplusd.api.UserAPI;
import com.cnlive.meplusd.model.ErrorMessage;
import com.cnlive.meplusd.ui.base.BaseActivity;
import com.cnlive.meplusd.utils.ActivityJumpUtils;
import com.cnlive.meplusd.utils.ActivityManageUtil;
import com.cnlive.meplusd.utils.RestAdapterUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
        mSimpleImage1.setImageURI(Uri.parse("http://yweb3.cnliveimg.com/img/cnlive/161121103649189_625.png"));

        mSimpleImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityJumpUtils.JumpComFinishActivity(SplashActivity.this,MainActivity.class);
            }
        });

        UserAPI userAPI = RestAdapterUtils.getRestAPI(Config.SJR_URL, UserAPI.class);
        userAPI.getInfo(new Callback<ErrorMessage>() {
            @Override
            public void success(ErrorMessage errorMessage, Response response) {
                String str = errorMessage.getErrorMessage().toString();
                mTvInfo.setText(str.toString());
                Log.i("TSM", "str==="+str);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("TSM", "error==="+error.toString());
            }
        });

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
