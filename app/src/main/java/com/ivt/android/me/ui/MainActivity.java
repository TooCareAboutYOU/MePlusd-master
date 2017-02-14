package com.ivt.android.me.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ivt.android.me.R;
import com.ivt.android.me.ui.base.BaseActivity;
import com.ivt.android.me.ui.fragment.MyInfoFragment;
import com.ivt.android.me.ui.fragment.TabHomeFragment;
import com.ivt.android.me.ui.fragment.TabLiveFragment;

import butterknife.Bind;


public class MainActivity extends BaseActivity {


    @Bind(R.id.img_toolback)
    ImageView mImgToolback;
    @Bind(R.id.tv_tooltitle)
    TextView mTvTooltitle;
    @Bind(R.id.img_toolmenu)
    ImageView mImgToolmenu;
    private Class fragmentArray[] = {TabHomeFragment.class, TabLiveFragment.class, MyInfoFragment.class};

    private int mImageViewArray[] = {R.drawable.tab_img_selector, R.drawable.btn_open_live, R.drawable.tab_myinfo_img_selector};

    private String mTextviewArray[] = {"首页", "", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgToolback.setImageResource(R.drawable.ic_launcher);
        mImgToolmenu.setImageResource(R.drawable.btn_open_live);
        mTvTooltitle.setText("蜜家");

        FragmentTabHost fg = (FragmentTabHost) findViewById(R.id.tabhost);
        fg.setup(this, getSupportFragmentManager(), R.id.fragment);
        int count = fragmentArray.length;
        //循环放置
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = fg.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            fg.addTab(tabSpec, fragmentArray[i], null);
        }
        //去掉了底部导航栏的间隔竖线
        fg.getTabWidget().setDividerDrawable(null);
    }

    //给导航栏放置图片和标题
    private View getTabItemView(int index) {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_content, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.tab_img);
        imageView.setImageResource(mImageViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.tab_title);
        textView.setText(mTextviewArray[index]);
        if (index == 1) {
            textView.setVisibility(View.GONE);
        }
        return view;
    }

}
