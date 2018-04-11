package com.han.hanmaxmin.common.utils.helper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.ImageWriter;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.common.utils.glidetransform.RoundBitmapTransform;

import java.io.File;
import java.net.URL;

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

    public Builder createRequest() {
        return new Builder(HanHelper.getInstance().getApplication());
    }

    public Builder createRequest(Context context) {
        return new Builder(context);
    }

    public Builder createRequest(Activity activity) {
        return new Builder(activity);
    }

    public Builder createRequest(FragmentActivity fragmentActivity) {
        return new Builder(fragmentActivity);
    }

    public Builder createRequest(Fragment fragment) {
        return new Builder(fragment);
    }

    public Builder createRequest(android.support.v4.app.Fragment fragment) {
        return new Builder(fragment);
    }

    public Builder createRequest(View view) {
        return new Builder(view);
    }

    @ThreadPoint(ThreadType.MAIN)
    public void clearMemoryCache() {
        Glide.get(HanHelper.getInstance().getApplication()).clearMemory();
    }

    public void clearMemoryDiskCache() {
        Glide.get(HanHelper.getInstance().getApplication()).clearDiskCache();
    }

    public long getCacheSize() {
        try {
            File photoCacheDir = Glide.getPhotoCacheDir(HanHelper.getInstance().getApplication());
            return getFolderSize(photoCacheDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getCacheFormatSize(){
        return  Formatter.formatFileSize(HanHelper.getInstance().getApplication(), getCacheSize());
    }

    public class Builder {
        private boolean enableDefaultHolder = true;
        private RequestManager manager;
        private Object mObject;
        private int placeholderId;// 占位符Id
        private int errorId;
        private int defaultHolderId;// 默认占位符
        private Drawable placeholderDrawable;
        private Drawable errorDrawable;
        private boolean centerCrop;
        private boolean fitCenter;
        private boolean centerInside;
        private int mCorners;
        private int mWidth;
        private int mHeight;
        private boolean noMemoryCache;
        private DiskCacheStrategy diskCacheStrategy;

        Builder(Context context) {
            manager = Glide.with(context);
            defaultHolderId = HanHelper.getInstance().getApplication().defaultImageHolder();
        }

        Builder(Activity context) {
            manager = Glide.with(context);
            defaultHolderId = HanHelper.getInstance().getApplication().defaultImageHolder();
        }

        Builder(Fragment context) {
            manager = Glide.with(context);
            defaultHolderId = HanHelper.getInstance().getApplication().defaultImageHolder();
        }

        Builder(android.support.v4.app.Fragment context) {
            manager = Glide.with(context);
            defaultHolderId = HanHelper.getInstance().getApplication().defaultImageHolder();
        }

        Builder(FragmentActivity context) {
            manager = Glide.with(context);
            defaultHolderId = HanHelper.getInstance().getApplication().defaultImageHolder();
        }

        Builder(View context) {
            manager = Glide.with(context);
            defaultHolderId = HanHelper.getInstance().getApplication().defaultImageHolder();
        }

        public RequestManager getManager() {
            return manager;
        }

        public Builder load(String url) {
            if (!TextUtils.isEmpty(url)) this.mObject = createGlideUrl(url, url);
            return this;
        }

        public Builder load(Object object) {
            this.mObject = object;
            return this;
        }

        public Builder load(String url, boolean ignoreParamsKey) {
            if (TextUtils.isEmpty(url)) return this;
            if (ignoreParamsKey) {
                Uri uri = Uri.parse(url);
                String uriWithoutKey = uri.getScheme() + "://" + uri.getHost() + uri.getPath();
                return load(url, uriWithoutKey);
            } else {
                this.mObject = createGlideUrl(url, url);
            }

            return this;
        }

        public Builder load(String url, String cacheKey) {
            if (!TextUtils.isEmpty(url)) this.mObject = createGlideUrl(url, cacheKey);
            return this;
        }

        public Builder resize(int width, int height) {
            this.mWidth = width;
            this.mHeight = height;
            return this;
        }

        public Builder placeholder(int resourceId) {
            this.placeholderId = resourceId;
            return this;
        }

        public Builder placeholder(Drawable drawable) {
            this.placeholderDrawable = drawable;
            return this;
        }

        public Builder error(int resourceId) {
            this.errorId = resourceId;
            return this;
        }

        public Builder error(Drawable drawable) {
            this.errorDrawable = drawable;
            return this;
        }

        public Builder centerCrop() {
            this.centerCrop = true;
            return this;
        }

        public Builder fitCenter() {
            this.fitCenter = true;
            return this;
        }

        public Builder centerInside() {
            this.centerInside = centerInside;
            return this;
        }

        public Builder roundedCorners(int corners) {
            this.mCorners = corners;
            return this;
        }

        public Builder noMemoryCache() {
            this.noMemoryCache = true;
            return this;
        }

        public Builder noDiskCache() {
            this.diskCacheStrategy = DiskCacheStrategy.NONE;
            return this;
        }

        public Builder enableDefaultHolder(boolean enable) {
            this.enableDefaultHolder = enable;
            return this;
        }

        public void into(ImageView view) {
            into(view, null);
        }

        public void into(ImageView view, final ImageRequestListener listener) {
            RequestBuilder<Drawable> requestBuilder;
            if (mObject != null) {
                requestBuilder = manager.load(mObject);
            } else {
                L.e(TAG, "method error, load(...) params is empty...");
                return;
            }

            setRequestOptionsIfNeed(requestBuilder);
            if(listener != null){
                requestBuilder.listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(e != null){
                            listener.onLoadFailed(e.getMessage());
                            for (Throwable t : e.getRootCauses()){
                                L.e(TAG, "Caused by " + t.getMessage());
                            }
                        } else {
                            listener.onLoadFailed("");
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        listener.onSuccess(resource, model);
                        return false;
                    }
                });
            }
            requestBuilder.into(view);
        }

        private void setRequestOptionsIfNeed(RequestBuilder requestBuilder) {
            if (shouldCreateReqeustOptions()) {
                RequestOptions requestOptions = createRequestOptions();

            }
        }


        private boolean shouldCreateReqeustOptions() {
            return placeholderId > 0
                    || errorId > 0
                    || placeholderDrawable != null
                    || errorDrawable != null
                    || (enableDefaultHolder && defaultHolderId > 0)
                    || centerCrop
                    || fitCenter
                    || centerInside
                    || mCorners > 0
                    || (mWidth > 0 && mHeight > 0)
                    || noMemoryCache
                    || diskCacheStrategy != null;
        }

        private RequestOptions createRequestOptions() {
            RequestOptions requestOptions = new RequestOptions();
            if (placeholderId > 0) {
                requestOptions.placeholder(placeholderId);
            } else if (placeholderDrawable != null) {
                requestOptions.placeholder(placeholderDrawable);
            } else if (enableDefaultHolder && defaultHolderId > 0) {
                requestOptions.placeholder(defaultHolderId);
            }

            if (errorId > 0) {
                requestOptions.error(errorId);
            } else if (errorDrawable != null) {
                requestOptions.error(errorDrawable);
            } else if (enableDefaultHolder && defaultHolderId > 0) {
                requestOptions.error(defaultHolderId);
            }

            if (mCorners > 0) {
                if (centerCrop) {
                    requestOptions.optionalCenterCrop();
                } else if (fitCenter) {
                    requestOptions.optionalFitCenter();
                } else if (centerInside) {
                    requestOptions.optionalCenterInside();
                }
                requestOptions.optionalTransform(new RoundBitmapTransform(mCorners));
            } else {
                if (centerCrop) {
                    requestOptions.centerCrop();
                } else if (fitCenter) {
                    requestOptions.fitCenter();
                } else if (centerInside) {
                    requestOptions.centerInside();
                }
            }

            if (mWidth > 0 && mHeight > 0) requestOptions.override(mWidth, mHeight);
            if (noMemoryCache) requestOptions.skipMemoryCache(true);
            if (diskCacheStrategy != null) requestOptions.diskCacheStrategy(diskCacheStrategy);
            return requestOptions;
        }


        private MyGlideUrl createGlideUrl(String url, String cacheKey) {
            MyGlideUrl myGlideUrl = new MyGlideUrl(url);
            myGlideUrl.setCacheKey(cacheKey);
            return myGlideUrl;
        }
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     */
    private long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public interface ImageRequestListener {
        void onLoadFailed(String message);

        void onSuccess(Drawable drawable, Object model);
    }


    public class MyGlideUrl extends GlideUrl {

        private String mCacheKey;

        public MyGlideUrl(String url) {
            super(url);
        }

        void setCacheKey(String keyCache) {
            this.mCacheKey = keyCache;
        }

        @Override
        public String getCacheKey() {
            return TextUtils.isEmpty(mCacheKey) ? super.getCacheKey() : mCacheKey;
        }
    }
}

