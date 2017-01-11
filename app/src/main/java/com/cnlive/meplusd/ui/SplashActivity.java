package com.cnlive.meplusd.ui;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cnlive.meplusd.Config;
import com.cnlive.meplusd.R;
import com.cnlive.meplusd.api.UserAPI;
import com.cnlive.meplusd.model.ErrorMessage;
import com.cnlive.meplusd.ui.base.BaseActivity;
import com.cnlive.meplusd.utils.RestAdapterUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSimpleImage1.setImageURI(Uri.parse("http://yweb3.cnliveimg.com/img/cnlive/161121103649189_625.png"));

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
}
