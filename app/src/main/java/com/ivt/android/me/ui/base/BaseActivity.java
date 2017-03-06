package com.ivt.android.me.ui.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnlive.libs.user.model.UserData;
import com.ivt.android.me.R;
import com.ivt.android.me.utils.ActivityManageUtil;
import com.ivt.android.me.utils.CrashHandlerUtils;
import com.ivt.android.me.utils.ToastUtils;

import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected static UserData mUserData;

    @Nullable
    @Bind(R.id.tool_normal)
    protected
    Toolbar mToolbar;

    @Nullable
    @Bind(R.id.img_toolback)
    public ImageView custom_back;

    @Nullable
    @Bind(R.id.tv_tooltitle)
    public TextView custom_title;

    @Nullable
    @Bind(R.id.img_toolmenu)
    public ImageView custom_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //window.setNavigationBarColor(Color.BLACK);
        }

        DealWithException();
        ActivityManageUtil.getInstance().pushToStatic(BaseActivity.class);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        x.view().inject(this);
        setupToolbar();
    }

    private void DealWithException(){
        CrashHandlerUtils crashHandlerUtils= CrashHandlerUtils.getInstance();
        crashHandlerUtils.init(this);
    }

    protected void setupToolbar(){
        if (mToolbar != null){
            setSupportActionBar(mToolbar);
        }
    }


    public void setBackIcon(int drawableId){ custom_back.setBackgroundResource(drawableId); }

    public void setMenuIcon(int drawableId){ custom_menu.setBackgroundResource(drawableId); }

    public Toolbar getToolbar(){ return mToolbar; }

    public void setToolBarTitle(String title){
        if (custom_title != null){
            custom_title.setText(title);
        }
    }

    protected void hideTitle(boolean isVisiable){
        if (custom_title != null){
            custom_title.setVisibility(isVisiable ? View.VISIBLE : View.GONE);
        }
    }


    @Override
    protected void onResume() { super.onResume(); }

    @Override
    protected void onPause() { super.onPause();  }

    @Override
    protected void onDestroy() { super.onDestroy(); ButterKnife.unbind(this); }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;
    public void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            ToastUtils.CustomToast(this,"再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            ActivityManageUtil.getInstance().finishAllActivity();
            finish();
            System.exit(0);
        }
    }

    //退出当前actvity，返回上一activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            back();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void back(){
        ActivityManageUtil.getInstance().finishActivity(this);
        finish();
    }
}
