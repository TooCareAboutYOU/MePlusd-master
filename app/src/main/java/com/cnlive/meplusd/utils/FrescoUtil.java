package com.cnlive.meplusd.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by zhangshuai on 2016-11-22.
 */

public class FrescoUtil {

    /**
     * 加载app内非动图
     *
     * @param simpleDraweeView view控件
     * @param resId            资源比例
     * @param aspectRatio      图片长宽比例
     */
    public static void loadPicInApp(@NonNull SimpleDraweeView simpleDraweeView, @NonNull int resId, float aspectRatio) {
        if (simpleDraweeView == null)
            return;
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
        if (aspectRatio > 0) {
            simpleDraweeView.setAspectRatio(aspectRatio);
        }
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * 加载app内非动图
     *
     * @param simpleDraweeView
     * @param resId
     */
    public static void loadPicInApp(@NonNull SimpleDraweeView simpleDraweeView, @NonNull int resId) {
        if (simpleDraweeView == null)
            return;
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
        simpleDraweeView.setImageURI(uri);
    }

    /**
     * @param simpleDraweeView
     * @param resId
     * @param aspectRatio
     */
    public static void loadGifPicInApp(@NonNull SimpleDraweeView simpleDraweeView, @NonNull int resId, float aspectRatio) {
        if (simpleDraweeView == null) {
            return;
        }
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        if (aspectRatio > 0) {
            simpleDraweeView.setAspectRatio(aspectRatio);
        }
        simpleDraweeView.setController(draweeController);
    }

    /**
     * @param simpleDraweeView
     * @param resId
     */
    public static void loadGifPicInApp(@NonNull SimpleDraweeView simpleDraweeView, @NonNull int resId) {
        if (simpleDraweeView == null) {
            return;
        }
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setController(draweeController);
    }

    /**
     * @param simpleDraweeView
     * @param gifUrl
     * @param aspectRatio
     */
    public static void loadGifPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String gifUrl, float aspectRatio) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(gifUrl))
                .setAutoPlayAnimations(true)
                .build();
        if (aspectRatio > 0) {
            simpleDraweeView.setAspectRatio(aspectRatio);
        }
        simpleDraweeView.setController(draweeController);
    }

    /**
     * @param simpleDraweeView
     * @param gifUrl
     */
    public static void loadGifPicOnNet(SimpleDraweeView simpleDraweeView, @NonNull String gifUrl) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(gifUrl))
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setController(draweeController);
    }

}
