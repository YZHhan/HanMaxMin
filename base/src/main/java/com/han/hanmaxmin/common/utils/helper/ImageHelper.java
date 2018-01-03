package com.han.hanmaxmin.common.utils.helper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.utils.glidewithokhttp.OkHttpGlideUrl;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  14:10
 * @Description 图片加载工具类
 * Glide。
 */

public class ImageHelper {
    private static final String TAG = "ImageHelper";
    private static ImageHelper helper;

    private ImageHelper() {
    }

    public static ImageHelper getInstance() {
        if (helper == null) {
            synchronized (ImageHelper.class) {
                if (helper == null) helper = new ImageHelper();
            }
        }
        return helper;
    }

    public ImageBuilder createRequest() {
        return new ImageBuilder(HanHelper.getInstance().getApplication());
    }

    public ImageBuilder createRequest(Context context) {
        return new ImageBuilder(context);
    }

    public ImageBuilder createRequest(Activity activity) {
        return new ImageBuilder(activity);
    }

    public ImageBuilder createRequest(FragmentActivity fragmentActivity) {
        return new ImageBuilder(fragmentActivity);
    }

    public ImageBuilder createRequest(Fragment fragment) {
        return new ImageBuilder(fragment);
    }

    public ImageBuilder createRequest(android.support.v4.app.Fragment fragment) {
        return new ImageBuilder(fragment);
    }

    public ImageBuilder createRequest(View context) {
        return new ImageBuilder(context);
    }

    public void clearMemeoryCache() {
        Glide.get(HanHelper.getInstance().getApplication()).clearMemory();
    }

    public void clearMemeoryDiskCache() {
        Glide.get(HanHelper.getInstance().getApplication()).clearDiskCache();
    }


    public class Builder {
        private RequestManager    manager;
        private Object            mObeject;
        private Drawable          mDrawable;
        private int               placeholderId;
        private int               errorId;
        private Drawable          errorDrawable;
        private boolean           centerCrop;
        private boolean           fifCenter;
        private boolean           centerInside;
        private int               mCorners;
        private int               mWidth;
        private int               mHeight;
        private boolean           noMemoryCache;
        private DiskCacheStrategy diskCacheStrategy;

        Builder(Context context) {
            manager = Glide.with(context);
        }

        Builder(Activity context) {
            manager = Glide.with(context);
        }

        Builder(Fragment context) {
            manager = Glide.with(context);
        }

        Builder(android.support.v4.app.Fragment context) {
            manager = Glide.with(context);
        }

        Builder(FragmentActivity context) {
            manager = Glide.with(context);
        }

        Builder(View context) {
            manager = Glide.with(context);
        }

        public RequestManager getManager(){
            return manager;
        }

        public Builder load(String url){
            this.mObeject=new OkHttpGlideUrl(url, url);
            return this;
        }

        public Builder load(Object object){
            this.mObeject=object;
            return this;
        }

        public Builder laod (String url, boolean ignoreParamsKey){
            if(TextUtils.isEmpty(url))return this;
            if(ignoreParamsKey){
                Uri uri = Uri.parse(url);
                String uriWithoutKey = uri.getScheme() + "://" + uri.getHost() + uri.getPath();

            }

            return this;
        }




    }

}

