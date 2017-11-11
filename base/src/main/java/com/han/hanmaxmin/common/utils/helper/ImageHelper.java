package com.han.hanmaxmin.common.utils.helper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.han.hanmaxmin.common.utils.HanHelper;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  14:10
 * @Description  图片加载工具类
 *  Glide。
 */

public class ImageHelper {
    private static final String TAG="ImageHelper";
    private static ImageHelper helper;
    private ImageHelper() {
    }

    public static ImageHelper getInstance(){
        if (helper==null){
            synchronized (ImageHelper.class){
                if(helper==null)helper=new ImageHelper();
            }
        }
        return helper;
    }

    public ImageBuilder createRequest(){
        return new ImageBuilder(HanHelper.getInstance().getApplication());
    }

    public ImageBuilder createRequest(Context context){
        return new ImageBuilder(context);
    }

    public ImageBuilder createRequest(Activity activity){
        return new ImageBuilder(activity);
    }

    public ImageBuilder createRequest(FragmentActivity fragmentActivity) {
        return new ImageBuilder(fragmentActivity);
    }

    public ImageBuilder createRequest(Fragment fragment) {
        return new ImageBuilder(fragment);
    }

    public ImageBuilder createRequest(android.support.v4.app.Fragment fragment){
        return new ImageBuilder(fragment);
    }

    public ImageBuilder createRequest(View context){
        return new ImageBuilder(context);
    }

    public void clearMemeoryCache(){
       Glide.get(HanHelper.getInstance().getApplication()).clearMemory();
    }

    public void clearMemeoryDiskCache(){
        Glide.get(HanHelper.getInstance().getApplication()).clearDiskCache();
    }



}

