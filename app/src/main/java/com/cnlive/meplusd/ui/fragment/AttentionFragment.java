package com.cnlive.meplusd.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnlive.meplusd.ui.base.BaseFragment;

/**
 * @创建者 xk
 * @创建时间 2017/2/9 16:35
 * @描述 ${TODO}
 */
public class AttentionFragment extends BaseFragment {

    public AttentionFragment(Context context) {
        super(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(mContext);
        textView.setText("关注");
        return textView;
    }
}