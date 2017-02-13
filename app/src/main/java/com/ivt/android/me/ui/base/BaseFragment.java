package com.ivt.android.me.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.cnlive.libs.user.model.UserData;

/**
 * @创建者 xk
 * @创建时间 2017/2/9 16:32
 * @描述 ${TODO}
 */
public class BaseFragment extends Fragment{
    protected Context mContext;

    protected static UserData mUserData;

    public BaseFragment(Context context) {
        mContext = context;
    }
}
