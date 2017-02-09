package com.cnlive.meplusd.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.Toast;

import com.cnlive.meplusd.R;

import static com.cnlive.meplusd.utils.LogUtils.LOGD;


public class MainActivity extends TabActivity {


    private TabHost host;
    private TabHost.TabSpec tab1, tab2, tab3;
    private View view1, view2, view3;
    private LayoutInflater inflater;
    private static long back_pressed;
    private String preTab = "tab1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inflater = LayoutInflater.from(this);
        host = this.getTabHost();


        tab1 = host.newTabSpec("tab1");   //创建选项卡其中的参数不是固定的，只是一个标志
        startActivity(R.layout.tab_home, tab1, view1, TabHomeActivity.class);

        tab2 = host.newTabSpec("tab2");
        startActivity(R.layout.tab_live, tab2, view2, TabLiveActivity.class);

        tab3 = host.newTabSpec("tab3");
        startActivity(R.layout.tab_my, tab3, view3, MyInfoActivity.class);


        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                preTab = tabId;
            }
        });

    }


    private void startActivity(int resource, TabHost.TabSpec tab, View view, Class cls) {
        view = inflater.inflate(resource, null);
        tab.setIndicator(view);  //设置标题
        Intent intent = new Intent(this, cls);
        tab.setContent(intent);  //这是跳转内容
        host.addTab(tab);  //添加到host中
    }


    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
           super.onBackPressed();
            LOGD("xk==按了两次");
        } else {
            Toast.makeText(this, "再次点击退出！", Toast.LENGTH_SHORT).show();
            LOGD("xk==按了一次");
        }

        back_pressed = System.currentTimeMillis();
    }


}
