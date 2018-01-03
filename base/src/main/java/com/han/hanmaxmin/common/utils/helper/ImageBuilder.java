package com.han.hanmaxmin.common.utils.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.model.GlideUrl;

/**
 * @CreateBy Administrator
 * @Date 2017/11/6  16:53
 * @Description ImageHelper  的构建者类
 */

public class ImageBuilder {

    private RequestManager manager;
    private Object obeject;
//    private Drawable

    public ImageBuilder(Context context) {
        manager = Glide.with(context);
    }

    public ImageBuilder(Activity activity) {
        manager = Glide.with(activity);
    }

    public ImageBuilder(android.app.Fragment fragment) {
        manager = Glide.with(fragment);
    }

    public ImageBuilder(Fragment fragment) {
        manager = Glide.with(fragment);
    }

    public ImageBuilder(FragmentActivity fragmentActivity) {
        manager = Glide.with(fragmentActivity);
    }

    public ImageBuilder(View context) {
        manager = Glide.with(context);
    }

    public RequestManager getManager() {
        return manager;
    }

    public RequestBuilder<Drawable> load(String url){
       return manager.load(url);
    }

    public RequestBuilder<Drawable> load(Object object){
        return manager.load(object);
    }

    public RequestBuilder<Drawable> loadIgnoreParamsKey(String url){
        return manager.load(new MyGlideUrl(url));
    }





    /**
     * 将图片的url除去param后作为缓存key，避免同一张图片缓存key不一致的问题
     */
    private class MyGlideUrl extends GlideUrl{
        private String cacheKey;
        public MyGlideUrl(String url) {
            super(url);
            if(!TextUtils.isEmpty(url)){
                Uri uri = Uri.parse(url);
                cacheKey=uri.getScheme()+"://"+uri.getHost()+uri.getPath();
            }
        }
        @Override public String getCacheKey() {
            return cacheKey;
        }
    }

}
