package com.ivt.android.me.utils;

import android.content.Context;

import java.io.File;

/**
 * Created by zhangshuai on 2017-01-11.
 * 清除本应用SharedPreference
 */

public class CleanSharedPreferenceUtils {


    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
    public static void cleanSharedPreference(Context context,String sharedFileName) {
        deleteFilesByDirectory(new File("/data/data/"+ AppUtils.getPackageInfo(context).packageName + sharedFileName));
    }

}
