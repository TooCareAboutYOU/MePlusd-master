package com.ivt.android.me.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivt.android.me.ui.base.BaseFragment;

/**
 * @创建者 xk
 * @创建时间 2017/2/9 16:35
 * @描述 ${TODO}
 */
public class NewestFragment extends BaseFragment {

    public NewestFragment(Context context) {
        super(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(mContext);
        textView.setText("最新");
        return textView;
    }
}
