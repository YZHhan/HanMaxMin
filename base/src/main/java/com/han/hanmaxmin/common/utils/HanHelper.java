package com.han.hanmaxmin.common.utils;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.han.hanmaxmin.AsApplication;
import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.aspect.thread.ThreadType;
import com.han.hanmaxmin.common.http.HttpAdapter;
import com.han.hanmaxmin.common.threadpoll.HanThreadPollHelper;
import com.han.hanmaxmin.common.utils.helper.CacheHelper;
import com.han.hanmaxmin.common.utils.helper.ImageHelper;
import com.han.hanmaxmin.common.utils.helper.ScreenHelper;
import com.han.hanmaxmin.common.viewbind.ViewBind;
import com.han.hanmaxmin.common.viewbind.ViewBindImpl;
import com.han.hanmaxmin.mvp.fragment.HanFragment;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  14:03
 * @Description 帮助类中心
 */

public class HanHelper {
    private static final String TAG = "HanHelper";
    private static HanHelper helper;
    private        ViewBind  viewBind;
    private HttpAdapter httpAdapter;

    public static HanHelper getInstance() {
        if (helper == null) {
            synchronized (HanHelper.class) {
                if (helper == null) helper = new HanHelper();
            }
        }
        return helper;
    }

    public AsApplication getApplication() {
        return application;
    }

    private volatile AsApplication application;

    private HanHelper() {
    }

    public void init(AsApplication asApplication) {
        this.application = asApplication;
    }

    public ScreenHelper getScreenHelper() {
        return ScreenHelper.getInstance();
    }

    public CacheHelper getCacheHelper() {
        return CacheHelper.getInstance();
    }

    public ImageHelper getImageHelper() {
        return ImageHelper.getInstance();
    }

    public HanThreadPollHelper getThreadHelper() {
        return HanThreadPollHelper.getInstance();
    }

    public HttpAdapter getHttpHelper(){
        if(httpAdapter == null){
            synchronized (this){
                if(httpAdapter == null)httpAdapter =new HttpAdapter();
            }
        }
        return httpAdapter;
    }

    public ViewBind getViewBindHelper(){
        if(viewBind == null){
            synchronized (HanHelper.class){
                if(viewBind == null) viewBind = new ViewBindImpl();
            }
        }
        return viewBind;
    }

    public void resetHttpAdapter (){
        httpAdapter = null;
    }

    public void intent2Activity(Class clazz) {
        intent2Activity(clazz, null, 0, null);
    }

    public void intent2Activity(Class clazz, Bundle bundle) {
        intent2Activity(clazz, bundle, 0, null);
    }

    public void intent2Activity(Class clazz, int reqeustCode) {
        intent2Activity(clazz, reqeustCode);
    }

    public void intent2Activity(Class clazz, Bundle bundle, ActivityOptionsCompat optionsCompat) {
        intent2Activity(clazz, bundle, 0, optionsCompat);
    }

    public void intent2Activity(Class clazz, Bundle bundle, int requestCode, ActivityOptionsCompat optionsCompat) {
        FragmentActivity activity = getScreenHelper().currentActivity();
        if (clazz != null && activity != null) {
            Intent intent = new Intent();
            intent.setClass(activity, clazz);
            if (bundle != null) intent.putExtras(bundle);
            if (optionsCompat == null) {
                if (requestCode > 0) {
                    activity.startActivityForResult(intent, requestCode);
                } else {
                    activity.startActivity(intent);
                }
            } else {
                if (requestCode > 0) {
                    ActivityCompat.startActivityForResult(activity, intent, requestCode, optionsCompat.toBundle());
                } else {
                    ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
                }
            }
        }
    }


    public void commitFragment(Fragment fragment) {
        commitFragment(fragment, fragment.getClass().getSimpleName());
    }

    public void commitFragment(Fragment fragment, String tag) {
        commitFragment(fragment, tag);
    }

    public void commitFragment(int layoutId, Fragment fragment) {
        commitFragment(layoutId, fragment, fragment.getClass().getSimpleName());
    }

    public void commitFragment(int layoutId, Fragment fragment, String tag) {
        FragmentActivity activity = getScreenHelper().currentActivity();
        if (activity == null) return;
        commitFragment(activity.getSupportFragmentManager(), layoutId, fragment, tag);
    }

    @ThreadPoint(ThreadType.MAIN) public void commitFragment(FragmentManager fragmentManager, int layoutId, Fragment fragment, String tag) {
        if (fragment != null && !fragment.isAdded() && fragmentManager != null && layoutId > 0) {
            fragmentManager.beginTransaction().replace(layoutId, fragment, tag).setTransition(FragmentTransaction.TRANSIT_NONE).commitAllowingStateLoss();
        }
    }


    public void commitFragment(Fragment old, Fragment fragment) {
        commitFragment(old, fragment, fragment.getClass().getSimpleName());
    }

    public void commitFragment(Fragment old, Fragment fragment, String tag) {
        commitFragment(old, android.R.id.custom, fragment, tag);
    }

    public void commitFragment(Fragment old, int layoutId, Fragment fragment) {
        commitFragment(old, layoutId, fragment, fragment.getClass().getSimpleName());
    }

    public void commitFragment(Fragment old, int layoutId, Fragment fragment, String tag) {
        FragmentActivity activity = getScreenHelper().currentActivity();
        if (activity == null) return;
        commitFragment(activity.getSupportFragmentManager(), old, layoutId, fragment, tag);
    }

    @ThreadPoint(ThreadType.MAIN) public void commitFragment(FragmentManager fragmentManager, Fragment old, int layoutId, Fragment fragment, String tag) {
        if (layoutId > 0 && fragment != null && !fragment.isAdded() && fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (old != null) fragmentTransaction.detach(old);
            fragmentTransaction.replace(layoutId, fragment, tag).setTransition(FragmentTransaction.TRANSIT_NONE).commitAllowingStateLoss();
        }
    }

    public void commitBackStackFragment(Fragment fragment) {
        commitBackStackFragment(fragment, fragment.getClass().getSimpleName());
    }

    public void commitBackStackFragment(Fragment fragment, String tag) {
        commitBackStackFragment(android.R.id.custom, fragment, tag);
    }

    public void commitBackStackFragment(int layoutId, Fragment fragment) {
        commitBackStackFragment(layoutId, fragment, fragment.getClass().getSimpleName());
    }

    public void commitBackStackFragment(int layoutId, Fragment fragment, String tag) {
        FragmentActivity activity = getScreenHelper().currentActivity();
        if (activity == null) return;
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        commitFragment(supportFragmentManager, layoutId, fragment, tag);
    }

    @ThreadPoint(ThreadType.MAIN) public void commitBackStackFragment(FragmentManager fragmentManager, int layoutId, Fragment fragment, String tag) {
        if (layoutId > 0 && fragment != null && !fragment.isAdded() && fragmentManager != null) {
            if (fragment instanceof HanFragment) {
                int colorId = ((HanFragment) fragment).getBackgroundColorId();
                ((HanFragment) fragment).setBackgroundColorId(colorId > 0 ? colorId : R.color.color_bg);
            }
            fragmentManager.beginTransaction().add(layoutId, fragment, tag).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
        }
    }


    public void commitDialogFragment(DialogFragment dialogFragment) {
        FragmentActivity activity = getScreenHelper().currentActivity();
        if (dialogFragment == null || activity == null) return;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().add(dialogFragment, dialogFragment.getClass().getSimpleName()).commitAllowingStateLoss();
        }
    }


    public String getString(@StringRes int resId) {
        return getApplication().getString(resId);
    }

    public String getString(@StringRes int resId, Object... formatArgs) {
        return getApplication().getString(resId, formatArgs);
    }

    public Drawable getDrawable(@DrawableRes int resId) {
        return getApplication().getResources().getDrawable(resId);
    }

    public int getColor(@ColorRes int resId) {
        return getApplication().getResources().getColor(resId);
    }

    public float getDimension(@DimenRes int resId) {
        return getApplication().getResources().getDimension(resId);
    }

    public boolean isSdCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}
