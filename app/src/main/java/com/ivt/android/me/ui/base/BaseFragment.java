package com.ivt.android.me.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * @创建者 xk
 * @创建时间 2017/2/9 16:32
 * @描述 ${TODO}
 */
public abstract class BaseFragment extends Fragment{
    private View fragmentRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentRootView == null){
            fragmentRootView = inflater.inflate(getLayoutRes(), container, false);
            //fragmentRootView= x.view().inject(getLayoutRes(),inflater,container);
        }
        ViewGroup parent = (ViewGroup) fragmentRootView.getParent();
        if (parent != null) {
            parent.removeView(fragmentRootView);
        }

        initView(fragmentRootView);
        return fragmentRootView;
    }
    protected abstract int getLayoutRes();


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Fresco.getImagePipeline().clearMemoryCaches();
    }

    protected abstract void initView(View view);
}
