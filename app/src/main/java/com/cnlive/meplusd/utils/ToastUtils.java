package com.cnlive.meplusd.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cnlive.meplusd.R;

/**
 * Created by zhangshuai on 2017-01-11.
 */

public class ToastUtils {

    private ToastUtils()
    {
		/* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message)
    {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message)
    {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message)
    {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message)
    {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration)
    {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration)
    {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }


    /*
    * 底部提示
    * @param context
    * @param msg
    * */
    public static void CustomToast(Context context,String msg){
        Toast toast=null;
        if(toast == null){ toast = new Toast(context); }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        View view=LayoutInflater.from(context).inflate(R.layout.toast_down,null);
        TextView txtToast = (TextView)view.findViewById(R.id.tv_toast);
        txtToast.setText("\t"+msg+" \t");
        toast.setView(view);
        toast.show();
    }


    /*
    * 顶部提示
    * @param context
    * @param msg
    * */
    public static void CustomTopToast(Context context,String msg){
        Toast toast=null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=inflater.inflate(R.layout.toast_top,null);
        TextView txtToast = (TextView)view.findViewById(R.id.tv_toast);

        if (!TextUtils.isEmpty(msg)){ txtToast.setText("\t"+msg+" \t"); }

        if(toast == null){
            toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.FILL_HORIZONTAL|Gravity.TOP, 0, 0);
        }

        toast.setView(view);
        toast.show();
    }

}
