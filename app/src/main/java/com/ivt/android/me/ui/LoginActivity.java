package com.ivt.android.me.ui;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cnlive.libs.user.IUserService;
import com.cnlive.libs.user.UserUtil;
import com.cnlive.libs.user.model.UserData;
import com.cnlive.libs.util.data.network.Callback;
import com.ivt.android.me.Configs;
import com.ivt.android.me.R;
import com.ivt.android.me.api.SmsInterface;
import com.ivt.android.me.api.UserAPI;
import com.ivt.android.me.livesdk.UserSdk;
import com.ivt.android.me.model.ErrorMessage;
import com.ivt.android.me.ui.base.BaseActivity;
import com.ivt.android.me.utils.ActivityJumpUtils;
import com.ivt.android.me.utils.AppUtils;
import com.ivt.android.me.utils.RestAdapterUtils;
import com.ivt.android.me.utils.ToastUtils;


import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener{


    /*
    *  http://blog.csdn.net/qiantujava/article/details/9903891
    *  http://blog.csdn.net/getchance/article/details/8478993  手势监听刷新控件使用
    * */


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

    /* 账号登录 测试账号：13816765902  123456  uid: 1222679  */
    /*请求活动id http://192.168.6.172:8080/portal-clt/getAnchorActivityId.html?uid=1222679  */
    @Bind(R.id.et_aphoneNum)
    EditText mEtAphoneNum;
    @Bind(R.id.et_accountpwd)
    EditText mEtAccountpwd;
    @Bind(R.id.btn_accountLogin2)
    Button mBtnAccountLogin2;
    @Bind(R.id.btn_xGet)
    Button btn_xget;

    /*手机号码修改账号密码*/
    @Bind(R.id.et_uphonenum)
    EditText et_getphone;
    @Bind(R.id.tv_upCode)
    EditText tv_upCode;
    @Bind(R.id.btn_upgetcode)
    Button btn_upgetcode;
    @Bind(R.id.btn_updatepwd)
    Button btn_updatepwd;


    private int isSeccussLogin = 0;

    private UserAPI userAPI;

    private SmsContent smsContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userAPI = RestAdapterUtils.getRestAPI(Configs.SJR_URL, UserAPI.class);
        mBtnAccountLogin.setOnClickListener(this);
        mBtnGetcode.setOnClickListener(this);

        mBtnPgetcode.setOnClickListener(this);
        mBtnQuickLogin.setOnClickListener(this);

        mBtnAccountLogin2.setOnClickListener(this);

        btn_xget.setOnClickListener(this);

        btn_upgetcode.setOnClickListener(this);

        btn_updatepwd.setOnClickListener(this);

//        if (this.checkSelfPermission(Manifest.permission.READ_SMS)
//                != PackageManager.PERMISSION_GRANTED) {
//            //申请READ_SMS权限
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS}, 1);
//        }

        smsContent=new SmsContent(new Handler(),this);
        //注册短信变化监听
        this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsContent);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getcode:  //注册账号：获取验证码
                getCode();
                break;
            case R.id.btn_accountLogin:  //注册账号
                getUserInfo();
                break;
            case R.id.btn_pgetcode:  //快捷登录获取验证码
                quickgetCode();
                break;
            case R.id.btn_quickLogin:  //快捷登录
                timer.cancel();
                mBtnPgetcode.setText("重新获取");
                //mBtnPgetcode.setFocusable(true);
                mBtnPgetcode.setEnabled(true);
                mBtnPgetcode.setAlpha(1f);

                quickLogin();
                break;
            case R.id.btn_accountLogin2:  //账号登录
                AccountLogin();
                break;
            case R.id.btn_xGet:   //xUtils3 网络请求：GET方式
                //xGet1();
                xGet2();
                break;
            case R.id.btn_upgetcode:  //修改账号密码：获取验证码
                getUpdateCode();
                break;
            case R.id.btn_updatepwd:  // 确认修改
                updatePwd();
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
                        Toast.makeText(LoginActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
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

    //修改账号密码：获取验证码
    private void getUpdateCode(){
        /**
         *  找回或修改密码前发手机验证码
         *
         * @param type
         * @param mobile        手机号
         * @param callback      回调
         *
         * 发送手机验证码需要传入一个type值用来区分发送的验证码是用来做什么的
         * QUICK_LOGIN--->快捷登陆前发送手机验证码
         * REGISTER_OR_MODIFY_MOBILE---> 注册或修改手机号前发手机验证码
         * RETRIEVE_OR_MODIFY_PASSWORD---> 找回或修改密码前发手机验证码
         */
        UserUtil.sendMessage(IUserService.RETRIEVE_OR_MODIFY_PASSWORD,et_getphone.getText().toString(), new Callback() {
            @Override
            public void onState(int what, String extra, Object obj) {
                switch (what) {
                    case IUserService.SUCCESS:
                        //成功
                        Toast.makeText(LoginActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
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
                    case IUserService.ERROR_MOBILE_NOT_REGISTER:
                        Log.e(TAG, IUserService.message_error_mobile_not_register);
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

    //确认修改账号密码
    private void updatePwd(){
        /**
         * 通过手机号修改密码
         *
         * @param mobile           手机号
         * @param verificationCode 手机验证码
         * @param newPwd           新密码
         * @param callback         回调
         */
        UserUtil.modifyPwdWithMob(et_getphone.getText().toString(), tv_upCode.getText().toString(), "123456", new Callback() {
            @Override
            public void onState(int what, String extra, Object obj) {
                switch (what) {
                    case IUserService.SUCCESS:
                        //成功
                        Toast.makeText(LoginActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        break;
                    case IUserService.ERROR_PARAM:
                        Log.e(TAG, IUserService.message_error_param);
                        break;
                    case IUserService.ERROR_MOBILE:
                        Log.e(TAG, IUserService.message_error_mobile);
                        break;
                    case IUserService.ERROR_VERIFICATION_CODE_LENGTH:
                        Log.e(TAG, IUserService.message_error_verfication_code_length);
                        break;
                    case IUserService.ERROR_PASSWORD_CONTENT:
                        Log.e(TAG, IUserService.message_error_password_content);
                        break;
                    case IUserService.ERROR_VERIFICATION_CODE:
                        Log.e(TAG, IUserService.message_error_verfication_code);
                        break;
                    case IUserService.ERROR_MOBILE_NOT_REGISTER:
                        Log.e(TAG, IUserService.message_error_mobile_not_register);
                        break;
                    case IUserService.ERROR_OTHER:
                        Log.e(TAG, IUserService.message_error_other);
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
                                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
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

    //快捷登录  获取验证码
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

    /*验证码倒计时*/
    public CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mBtnPgetcode.setText((millisUntilFinished / 1000) + "s重新获取");
            mBtnPgetcode.setEnabled(false);
            mBtnPgetcode.setAlpha(0.5f);
        }

        @Override
        public void onFinish() {
            mBtnPgetcode.setText("重新获取");
            //mBtnPgetcode.setFocusable(true);
            mBtnPgetcode.setEnabled(true);
            mBtnPgetcode.setAlpha(1f);
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

    //账号登录
    private void AccountLogin() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中....");
        progressDialog.show();

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
                        LoginService(mUserData,progressDialog);
                        Log.i(TAG,mUserData.toString());
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
    private void LoginService(UserData mUserData,final ProgressDialog progressDialog) {
        String str = UserSdk.LoginService(mUserData);
        String versionNum = String.valueOf(AppUtils.getVersionCode(LoginActivity.this));
        userAPI.SynchronousUserData(versionNum, "a", str, new retrofit.Callback<ErrorMessage>() {
            @Override
            public void success(ErrorMessage errorMessage, Response response) {
                Toast.makeText(LoginActivity.this, "登录状态：" + errorMessage.getErrorCode(), Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
                if (errorMessage.getErrorCode().equals("0")){
                    btn_xget.setEnabled(true);
                }
            }

            @Override
            public void failure(RetrofitError error) { }
        });
    }

    //xUtils3-——GET
    //@Event(value = R.id.btn_xGet, type = View.OnClickListener.class)
    private org.xutils.common.Callback.Cancelable cancelable;
    public void xGet1() {
        String url = Configs.SJR_URL + "/zGWUserSynchronization.html";
        String str = UserSdk.LoginService(mUserData);
        String versionNum = String.valueOf(AppUtils.getVersionCode(LoginActivity.this));

        if (url.equals("") || url==null){
            Toast.makeText(this, "地址不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (str.equals("") || str==null){
            Toast.makeText(this, "云端数据不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (versionNum.equals("") || versionNum==null){
            Toast.makeText(this, "版本号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中....");
        progressDialog.show();

        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("version", versionNum);
        params.addQueryStringParameter("plat", "a");
        params.addQueryStringParameter("data", str);
        cancelable = x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String msg) {
                ErrorMessage message= JSON.parseObject(msg,ErrorMessage.class);
                Toast.makeText(LoginActivity.this, "请求状态1：成功 \n" + message.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(LoginActivity.this, "请求状态2：错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(LoginActivity.this, "请求状态3：被取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                progressDialog.cancel();
                Toast.makeText(LoginActivity.this, "请求状态4：完成", Toast.LENGTH_SHORT).show();
                IsCancel(true,cancelable);
            }
        });

    }
    private void IsCancel(boolean isFinished,org.xutils.common.Callback.Cancelable cancelable){
        //主动调用取消请求
        if (isFinished) {
            cancelable.cancel();
            Toast.makeText(LoginActivity.this, "请求状态5：关闭请求", Toast.LENGTH_SHORT).show();
        }
    }

    public void xGet2() {
        String url = Configs.SJR_URL + "/zGWUserSynchronization.html";
        String str = UserSdk.LoginService(mUserData);
        String versionNum = String.valueOf(AppUtils.getVersionCode(LoginActivity.this));

        if (url.equals("") || url==null){
            Toast.makeText(this, "地址不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (str.equals("") || str==null){
            Toast.makeText(this, "云端数据不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (versionNum.equals("") || versionNum==null){
            Toast.makeText(this, "版本号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中....");
        progressDialog.show();

        Map map=new HashMap();
        map.put("version",versionNum);
        map.put("plat", "a");
        map.put("data", str);
//        xUtils3.Get(url,map,new xUtils3.MyCallBack<String>(){
//            @Override
//            public void onFinished() {
//                super.onFinished();
//                Toast.makeText(LoginActivity.this, "2_GET：完成", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSuccess(String result) {
//                super.onSuccess(result);
//                progressDialog.cancel();
//                Toast.makeText(LoginActivity.this, "2_GET：成功"+result.toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                super.onError(ex, isOnCallback);
//                Toast.makeText(LoginActivity.this, "2_GET：错误", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                super.onCancelled(cex);
//                Toast.makeText(LoginActivity.this, "2_GET：被取消", Toast.LENGTH_SHORT).show();
//            }
//        });

    }



    //上传
    @Event(value = R.id.btn_upload, type = View.OnClickListener.class)
    private void myUpload(View v){
//        CmsAPI cmsAPI=RestAdapterUtils.getRestAPI(Configs.SJR_URL,CmsAPI.class);
//        cmsAPI.getMainPageJson(new retrofit.Callback<T1>() {
//            @Override
//            public void success(T1 t1, Response response) {
//                ToastUtils.showLong(LoginActivity.this,"T1："+t1.toString());
//                LogUtils.LOGE("T1："+t1.toString());
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                ToastUtils.showLong(LoginActivity.this,"error："+error.toString());
//                LogUtils.LOGE("error："+error.toString());
//            }
//        });
//        String path="文件地址",url="上传地址";
//        RequestParams params=new RequestParams(url);
//        params.setMultipart(true);
//        params.addBodyParameter("file",new File(path));
//        x.http().post(params, new org.xutils.common.Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }

    //下载
    @Event(value = R.id.btn_download, type = View.OnClickListener.class)
    private void getWay(View v){


//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("加载中....");
//        progressDialog.show();
//        String url="https://www.baidu.com/img/bd_logo1.png";
//        String path=xUtils3.IMAGE_SDCARD_MADER+xUtils3.geFileName()+"bd_logo1.png";
//        xUtils3.DownLoadFile(url, path, new xUtils3.MyCallBack<File>(){
//            @Override
//            public void onSuccess(File result) {
//                super.onSuccess(result);
//
//                if (SDCardUtils.isSDCardEnable()){  //判断是否存在SD卡
//                    Toast.makeText(LoginActivity.this,"1：下载成功 \n 文件保存路径："+ result.getPath(), Toast.LENGTH_LONG).show();
//                }
//
//                //SimpleDraweeView img= (SimpleDraweeView) findViewById(R.id.img_download);
//                //img.setImageURI(Uri.parse(result.toString()));
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                super.onError(ex, isOnCallback);
//                ex.printStackTrace();
//                Toast.makeText(LoginActivity.this, "1：请求状态：错误", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                super.onCancelled(cex);
//                cex.printStackTrace();
//                Toast.makeText(LoginActivity.this, "1：下载失败，请检查网络和SD卡", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFinished() {
//                super.onFinished();
//                Toast.makeText(LoginActivity.this, "1：请求状态：完成", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//            }
//        });
    }



    //删除下载的图片
    @Event(value = R.id.btn_delete, type = View.OnClickListener.class)
    private void deletePic(View v){
//        String path=xUtils3.IMAGE_SDCARD_MADER+xUtils3.geFileName()+"bd_logo1.png";
//        File file_path=new File(path);
//        file_path.delete();
//        Toast.makeText(this, "删除图片地址：" + path, Toast.LENGTH_SHORT).show();
    }


    private void myDownload(View v){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中....");
        progressDialog.show();
        //http://wideo00.cnlive.com/video/data1/2016/0812/111917/3000/5ecc5b0567194d8fb0eb3db52240a091_111917_1_3000.m3u8
        String url="https://www.baidu.com/img/bd_logo1.png";
        String path=Environment.getExternalStorageDirectory()+"/AAAAAA/";
        RequestParams params=new RequestParams(url);
        //自定义保存路径
        params.setSaveFilePath(path);
        //自定义文件名
        params.setAutoRename(true);
        x.http().get(params, new org.xutils.common.Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Toast.makeText(LoginActivity.this,"下载成功 \n 文件保存路径："+ result.getPath(), Toast.LENGTH_LONG).show();
                //progressDialog.dismiss();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "请求状态：错误", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
                Toast.makeText(LoginActivity.this, "下载失败，请检查网络和SD卡", Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
            }
            @Override
            public void onFinished() {
                //progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "请求状态：完成", Toast.LENGTH_SHORT).show();
            }

            //网络请求之前回调
            @Override
            public void onWaiting() {
                Toast.makeText(LoginActivity.this, "请求状态：加载中...", Toast.LENGTH_SHORT).show();
            }
            //网络请求开始的时候回调
            @Override
            public void onStarted() {
                Toast.makeText(LoginActivity.this, "请求状态：开始", Toast.LENGTH_SHORT).show();
            }
            //下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //当前进度和文件总大小
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMax((int) total);
                progressDialog.setProgress((int) current);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityJumpUtils.JumpActivity(LoginActivity.this,MainActivity.class);
    }


    @Override
    protected void onStart() {
        super.onStart();
        smsContent.setSmsMessage(new SmsInterface() {
            @Override
            public void getSmsMessage(String msg) {
                ToastUtils.showShort(LoginActivity.this,"验证码："+msg);
                mEtPhonewCode.setText(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.getContentResolver().unregisterContentObserver(smsContent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // Permission Denied
            }
        }
    }
}

