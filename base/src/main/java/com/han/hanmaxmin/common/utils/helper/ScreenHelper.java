package com.han.hanmaxmin.common.utils.helper;

import android.support.v4.app.FragmentActivity;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;

import java.util.Stack;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  14:11
 * @Description  屏幕的帮助类，主要是用于Stack的入栈和出栈。
 * 补充一个Stack，peek()方法（返回的对象顶部的Stack，而不是移除他）
 *
 * 功能：
 *  入栈：Stack的add方法
 *  出栈：Stack的remove方法
 *  退出堆栈中所有Activity，当前Activity除外
 *  获取当前Activity，
 *
 *
 */

public class ScreenHelper {
    private static final String TAG = "ScreenHelper";
    private static ScreenHelper helper;

    private ScreenHelper() {
    }

    public static ScreenHelper getInstance() {
        if (helper == null) {
            synchronized (ScreenHelper.class) {
                if (helper == null) helper = new ScreenHelper();
            }
        }
        return helper;
    }

    /**
     * FragmentActivity堆栈 单例模式
     */
    private static final Stack<FragmentActivity> fragmentActivitys = new Stack();


    /**
     * 获取当前Activity
     * <p>
     * 但需要注意其中的peek()方法： 查看栈顶对象而不移除它
     */
    public FragmentActivity currentActivity() {
        if (fragmentActivitys.size() == 0) {
            L.i(TAG, "FragmentActivity stack size =0");
        }
        FragmentActivity fragmentActivity = fragmentActivitys.peek();
        L.i(TAG, "get current FragmentActivity " + fragmentActivity.getClass().getSimpleName() + "stack size :" + fragmentActivitys.size());
        return fragmentActivity;
    }

    /**
     * 入栈
     */
    public void pushActivity(FragmentActivity activity) {
        if (activity != null) {
            fragmentActivitys.add(activity);
            L.i(TAG, "activity入栈：" + activity.getClass().getSimpleName() + "当前栈大小：" + fragmentActivitys.size());
        } else {
            L.e(TAG, "push 传入的参数为空");
        }
    }

    public void popActivity(FragmentActivity activity) {
        if (activity != null) {
            activity.finish();
            fragmentActivitys.remove(activity);
            L.i(TAG,"activity出栈"+activity.getClass().getSimpleName()+"当前栈的大小："+fragmentActivitys.size());
        }else {
            L.i(TAG,"popActivity 传入的参数为空");
        }
        if(fragmentActivitys.size()<=0){//清除缓存
//            HanHelper.getInstance().getImageHelper().clearMemoryCache();
            HanHelper.getInstance().getThreadHelper().shutdown();

        }
    }
    /**
     * 退出堆栈中所有Activity, 当前的Activity除外
     * @param clazz 当前活动窗口
     */
    public void popAllActivityExceptMain(Class clazz){
        while (true){
            FragmentActivity activity=currentActivity();
            if(activity==null)break;
            if(activity.getClass().equals(clazz))break;
            popActivity(activity);
        }
    }





}
