package com.han.hanmaxmin.common.utils.glidewithokhttp;

import android.text.TextUtils;

import com.bumptech.glide.load.model.GlideUrl;

/**
 * @CreateBy Administrator
 * @Date 2017/12/21  10:33
 * @Description
 */

public class OkHttpGlideUrl extends GlideUrl {

    private String mCacheKey;

    public OkHttpGlideUrl(String url, String cacheKey) {
        super(url);
        if (!TextUtils.isEmpty(cacheKey)) {
            this.mCacheKey = cacheKey;
        } else {
            mCacheKey = url;
        }
    }

    @Override public String getCacheKey() {
        return mCacheKey;
    }
}
