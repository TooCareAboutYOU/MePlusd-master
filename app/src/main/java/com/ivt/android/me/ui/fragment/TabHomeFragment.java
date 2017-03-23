package com.ivt.android.me.ui.fragment;

import android.content.Intent;

import com.ivt.android.me.R;
import com.ivt.android.me.ui.AboutActivity;
import com.ivt.android.me.ui.LoginActivity;
import com.ivt.android.me.ui.SearchActivity;
import com.ivt.android.me.ui.base.BaseFragment;

import butterknife.OnClick;

/**
 * @创建者 xk
 * @创建时间 2017/2/10 16:12
 * @描述 ${TODO}
 */

public class TabHomeFragment extends BaseFragment {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @OnClick(R.id.btn_about)
    void AboutWay(){
        startActivity(new Intent(getActivity(), AboutActivity.class));
    }

    @OnClick(R.id.btn_search)
    void SearchWay(){
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    @OnClick(R.id.btn_login)
    void LoginWay(){
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
}
