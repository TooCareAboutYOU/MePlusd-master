package com.ivt.android.me.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.ivt.android.me.R;
import com.ivt.android.me.ui.AboutActivity;
import com.ivt.android.me.ui.LoginActivity;
import com.ivt.android.me.ui.SearchActivity;
import com.ivt.android.me.ui.base.BaseFragment;


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
    protected void initView(View view) {
        view.findViewById(R.id.btn_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });

        view.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        view.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }


}
