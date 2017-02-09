package com.cnlive.meplusd.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cnlive.meplusd.R;
import com.cnlive.meplusd.ui.base.BaseActivity;
import com.cnlive.meplusd.ui.fragment.AttentionFragment;
import com.cnlive.meplusd.ui.fragment.HotFragment;
import com.cnlive.meplusd.ui.fragment.NewestFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @创建者 xk
 * @创建时间 2017/2/8 15:01
 * @描述 首页
 */
public class TabHomeActivity extends BaseActivity {
    @Bind(R.id.home_tablayout)
    TabLayout mHomeTablayout;
    @Bind(R.id.home_viewpager)
    ViewPager mHomeViewpager;

    List<String> titles = new ArrayList<>();
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        custom_back.setImageResource(R.drawable.btn_open_live);
        custom_title.setText("首页");
        custom_menu.setImageResource(R.drawable.ic_launcher);
        titles.add("关注");
        titles.add("热门");
        titles.add("最新");

        initView();
    }

    private void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(new AttentionFragment(this));
        mFragments.add(new HotFragment(this));
        mFragments.add(new NewestFragment(this));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mHomeViewpager.setAdapter(viewPagerAdapter);
        mHomeTablayout.setupWithViewPager(mHomeViewpager);
        mHomeViewpager.setCurrentItem(1);
    }


    @OnClick({R.id.img_toolback, R.id.img_toolmenu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_toolback:
                break;
            case R.id.img_toolmenu:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        exitBy2Click();
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {  //TabLayout标题
            return titles.get(position);
        }

    }
}
