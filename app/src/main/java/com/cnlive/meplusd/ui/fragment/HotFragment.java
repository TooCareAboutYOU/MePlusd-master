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
 * @创建时间 2017/2/9 16:32
 * @描述 ${TODO}
 */
public class HotFragment extends BaseFragment {


    public HotFragment(Context context) {
        super(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(mContext);
        textView.setText("热门");
        return textView;
    }
}
