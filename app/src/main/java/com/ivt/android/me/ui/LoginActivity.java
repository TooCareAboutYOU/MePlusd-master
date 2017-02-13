package com.ivt.android.me.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cnlive.libs.user.IUserService;
import com.cnlive.libs.user.UserUtil;
import com.cnlive.libs.user.model.UserData;
import com.cnlive.libs.util.data.network.Callback;
import com.ivt.android.me.Configs;
import com.ivt.android.me.R;
import com.ivt.android.me.api.UserAPI;
import com.ivt.android.me.livesdk.UserSdk;
import com.ivt.android.me.model.ErrorMessage;
import com.ivt.android.me.ui.base.BaseActivity;
import com.ivt.android.me.utils.AppUtils;
import com.ivt.android.me.utils.RestAdapterUtils;
import com.ivt.android.me.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static String TAG = "LoginActivity";

    /* 快捷登录 */
    @Bind(R.id.et_qphoneNum)
    EditText mEtPhoneNum;
    @Bind(R.id.et_phonewCode)
    EditText mEtPhonewCode;
    @Bind(R.id.btn_pgetcode)
    Button mBtnPgetcode;
    @Bind(R.id.btn_quickLogin)
    Button mBtnQuickLogin;


    /* 注册 */
    @Bind(R.id.et_phonenum)
    EditText mEtPhonenum;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.et_code)
    EditText mEtCode;
    @Bind(R.id.btn_getcode)
    Button mBtnGetcode;
    @Bind(R.id.btn_accountLogin)
    Button mBtnAccountLogin;

    /* 账号登录 */
    @Bind(R.id.et_aphoneNum)
    EditText mEtAphoneNum;
    @Bind(R.id.et_accountpwd)
    EditText mEtAccountpwd;
    @Bind(R.id.btn_accountLogin2)
    Button mBtnAccountLogin2;

    private int isSeccussLogin = 0;

    private UserAPI userAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        userAPI= RestAdapterUtils.getRestAPI(Configs.URL,UserAPI.class);
        mBtnAccountLogin.setOnClickListener(this);
        mBtnGetcode.setOnClickListener(this);

        mBtnPgetcode.setOnClickListener(this);
        mBtnQuickLogin.setOnClickListener(this);

        mBtnAccountLogin2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getcode:  //注册获取验证码
                getCode();
                break;
            case R.id.btn_accountLogin:  //注册
                getUserInfo();
                break;

            case R.id.btn_pgetcode:  //快捷登录获取验证码
                quickgetCode();
                break;
            case R.id.btn_quickLogin:  //快捷登录
                quickLogin();
                break;
            case R.id.btn_accountLogin2:  //账号登录
                AccountLogin();

                break;
        }
    }

    //注册获取验证码
    private void getCode() {
        /**
         * 使用手机号注册前发送手机验证码
         * @param type
         * @param mobile        手机号
         * @param callback      回调
         *
         * 发送手机验证码需要传入一个type值用来区分发送的验证码是用来做什么的
         * TAG--->快捷登陆前发送手机验证码
         * REGISTER_OR_MODIFY_MOBILE---> 注册或修改手机号前发手机验证码
         * RETRIEVE_OR_MODIFY_PASSWORD---> 找回或修改密码前发手机验证码
         */
        UserUtil.sendMessage(IUserService.REGISTER_OR_MODIFY_MOBILE, mEtPhonenum.getText().toString(), new Callback() {
            @Override
            public void onState(int what, String extra, Object object) {
                switch (what) {
                    case IUserService.SUCCESS:
                        //成功
                        mBtnGetcode.setEnabled(false);
                        break;
                    case IUserService.ERROR_PARAM:
                        Log.e(TAG, IUserService.message_error_param);
                        break;
                    case IUserService.ERROR_MOBILE:
                        Log.e(TAG, IUserService.message_error_mobile);
                        break;
                    case IUserService.ERROR_CLIENT_PLAT:
                        Log.e(TAG, IUserService.message_error_client_plat);
                        break;
                    case IUserService.ERROR_MOBILE_EXIST:
                        Log.e(TAG, IUserService.message_error_mobile_exist);
                        break;
                    case IUserService.ERROR_MESSAGE_SERVER:
                        Log.e(TAG, IUserService.message_error_server);
                        break;
                    case IUserService.ERROR_MESSAGE_SERVER_ANALYSIS:
                        Log.e(TAG, IUserService.message_error_message_server_analysis);
                        break;
                    case IUserService.ERROR_OTHER:
                        Log.e(TAG, IUserService.message_error_other);
                        break;
                    case IUserService.ERROR_SEND_MESSAGE_TO_MUCH:
                        Log.e(TAG, IUserService.message_error_send_message_to_much);
                        break;
                    case IUserService.ERROR_SEND_MESSAGE_FAILED:
                        Log.e(TAG, IUserService.message_error_send_message_failed);
                        break;
                    case IUserService.ERROR_MESSAGE_OTHER:
                        Log.e(TAG, IUserService.message_error_message_other);
                        break;
                }
            }
        });
    }

    //注册
    private UserData getUserInfo() {
        /**
         * 注册
         *
         * @param userName         邮箱或手机
         * @param pwd              密码
         * @param verificationCode 手机验证码（手机号注册时必填）
         * @param invitationCode   邀请码(非必传参数,可以传"")
         * @param frmId            渠道ID(非必传参数,可传入"")
         * @param callback         回调
         */
        UserUtil.register(mEtPhonenum.getText().toString(), mEtPwd.getText().toString(), mEtCode.getText().toString(),
                "invitationCode", "frmId", new Callback() {
                    @Override
                    public void onState(int what, String extra, Object obj) {
                        switch (what) {
                            case IUserService.SUCCESS:
                                //成功 obj返回用户信息
                                isSeccussLogin = 1;
                                mUserData = ((UserData) obj);
                                break;
                            case IUserService.ERROR_PARAM:
                                Log.e(TAG, IUserService.message_error_param);
                                break;
                            case IUserService.ERROR_USER_NAME:
                                Log.e(TAG, IUserService.message_error_user_name);
                                break;
                            case IUserService.ERROR_VERIFICATION_CODE_LENGTH:
                                Log.e(TAG, IUserService.message_error_verfication_code_length);
                                break;
                            case IUserService.ERROR_MAIL:
                                Log.e(TAG, IUserService.message_error_mail);
                                break;
                            case IUserService.ERROR_PASSWORD_CONTENT:
                                Log.e(TAG, IUserService.message_error_password_content);
                                break;
                            case IUserService.ERROR_CLIENT_PLAT:
                                Log.e(TAG, IUserService.message_error_client_plat);
                                break;
                            case IUserService.ERROR_MOBILE_EXIST:
                                Log.e(TAG, IUserService.message_error_mobile_exist);
                                break;
                            case IUserService.ERROR_MAIL_EXIST:
                                Log.e(TAG, IUserService.message_error_mail_exist);
                                break;
                            case IUserService.ERROR_NICK_NAME_EXIST:
                                Log.e(TAG, IUserService.message_error_nick_name_exist);
                                break;
                            case IUserService.ERROR_VERIFICATION_CODE:
                                Log.e(TAG, IUserService.message_error_verfication_code);
                                break;
                            case IUserService.ERROR_OTHER:
                                Log.e(TAG, IUserService.message_error_other);
                                break;
                        }
                    }
                });

        if (isSeccussLogin == 1) {
            return mUserData;
        }

        return null;
    }

    // 快捷登录  获取验证码
    private void quickgetCode() {
        timer.start();
        /**
         * 快捷登录前发送手机验证码
         * @param type
         * @param mobile        手机号
         * @param callback      回调
         *
         * 发送手机验证码需要传入一个type值用来区分发送的验证码是用来做什么的
         * TAG--->快捷登陆前发送手机验证码
         * REGISTER_OR_MODIFY_MOBILE---> 注册或修改手机号前发手机验证码
         * RETRIEVE_OR_MODIFY_PASSWORD---> 找回或修改密码前发手机验证码
         */
        UserUtil.sendMessage(IUserService.QUICK_LOGIN, mEtPhoneNum.getText().toString(), new Callback() {
            @Override
            public void onState(int what, String extra, Object obj) {
                switch (what) {
                    case IUserService.SUCCESS:
                        //成功
                        timer.onFinish();
                        break;
                    case IUserService.ERROR_PARAM:
                        Log.e(TAG, IUserService.message_error_param);
                        break;
                    case IUserService.ERROR_MOBILE:
                        Log.e(TAG, IUserService.message_error_mobile);
                        break;
                    case IUserService.ERROR_CLIENT_PLAT:
                        Log.e(TAG, IUserService.message_error_client_plat);
                        break;
                    case IUserService.ERROR_MESSAGE_SERVER:
                        Log.e(TAG, IUserService.message_error_server);
                        break;
                    case IUserService.ERROR_MESSAGE_SERVER_ANALYSIS:
                        Log.e(TAG, IUserService.message_error_message_server_analysis);
                        break;
                    case IUserService.ERROR_OTHER:
                        Log.e(TAG, IUserService.message_error_other);
                        break;
                    case IUserService.ERROR_SEND_MESSAGE_TO_MUCH:
                        Log.e(TAG, IUserService.message_error_send_message_to_much);
                        break;
                    case IUserService.ERROR_SEND_MESSAGE_FAILED:
                        Log.e(TAG, IUserService.message_error_send_message_failed);
                        break;
                    case IUserService.ERROR_MESSAGE_OTHER:
                        Log.e(TAG, IUserService.message_error_message_other);
                        break;
                }
            }
        });
    }
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mBtnPgetcode.setText((millisUntilFinished / 1000) + "s重新获取");
            mBtnPgetcode.setEnabled(false);
            mBtnPgetcode.setAlpha(0.5f);
        }

        @Override
        public void onFinish() {
            mBtnPgetcode.setEnabled(true);
        }
    };
    //手机快捷登录
    private void quickLogin() {
        /**
         * 快捷登录
         * @param userName         手机号
         * @param verificationCode 验证码
         * @param frmId            渠道ID(非必传参数,可传"")
         * @param callback         回调
         */
        UserUtil.quickLogin(mEtPhoneNum.getText().toString(), mEtPhonewCode.getText().toString(), "", new Callback() {
            @Override
            public void onState(int what, String extra, Object obj) {
                switch (what) {
                    case IUserService.SUCCESS:
                        //成功  obj返回用户信息
                        mUserData = ((UserData) obj);
                        ToastUtils.CustomToast(LoginActivity.this, mUserData.toString());
                        break;
                    case IUserService.ERROR_PARAM:
                        Log.e(TAG, IUserService.message_error_param);
                        break;
                    case IUserService.ERROR_CLIENT_PLAT:
                        Log.e(TAG, IUserService.message_error_client_plat);
                        break;
                    case IUserService.ERROR_MOBILE:
                        Log.e(TAG, IUserService.message_error_mobile);
                        break;
                    case IUserService.ERROR_VERIFICATION_CODE_LENGTH:
                        Log.e(TAG, IUserService.message_error_verfication_code_length);
                        break;
                    case IUserService.ERROR_VERIFICATION_CODE:
                        Log.e(TAG, IUserService.message_error_verfication_code);
                        break;
                    case IUserService.ERROR_MOBILE_EXIST:
                        Log.e(TAG, IUserService.message_error_mobile_exist);
                        break;
                    case IUserService.ERROR_NICK_NAME_EXIST:
                        Log.e(TAG, IUserService.message_error_nick_name_exist);
                        break;
                    case IUserService.ERROR_OTHER:
                        Log.e(TAG, IUserService.message_error_other);
                        break;
                }
            }
        });
    }

    //快捷登录
    private void AccountLogin(){
        /**
         * 用户名&密码登录
         *
         * @param userName          手机邮箱或ID
         * @param pwd               密码
         * @param frmId             渠道ID(非必传参数,可以传"")
         * @param callback          回调
         */
        UserUtil.login(mEtAphoneNum.getText().toString(), mEtAccountpwd.getText().toString(), "frmId", new Callback() {
            @Override
            public void onState(int what, String extra, Object obj) {
                switch (what) {
                    case IUserService.SUCCESS:
                        //成功 obj返回用户信息
                        mUserData = ((UserData) obj);
                        LoginService(mUserData);
                        break;
                    case IUserService.ERROR_PARAM:
                        Log.e(TAG, IUserService.message_error_param);
                        break;
                    case IUserService.ERROR_USER_NOT_FOUND:
                        Log.e(TAG, IUserService.message_error_user_not_found);
                        break;
                    case IUserService.ERROR_USERNAME_OR_PASSWORD:
                        Log.e(TAG, IUserService.message_error_username_or_password);
                        break;
                    case IUserService.ERROR_PASSWORD:
                        Log.e(TAG, IUserService.message_error_password);
                        break;
                }
            }
        });
    }

    //通知服务端登录状态(成功、失败)
    private void LoginService(UserData mUserData){
        String str= UserSdk.LoginService(mUserData);
        String versionNum=String.valueOf(AppUtils.getVersionCode(LoginActivity.this));
        userAPI.SynchronousUserData(versionNum, "a", str, new retrofit.Callback<ErrorMessage>() {
            @Override
            public void success(ErrorMessage errorMessage, Response response) {
                Toast.makeText(LoginActivity.this, "登录状态："+errorMessage.getErrorCode(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {  }
        });
    }

}
