package com.han.hanmaxmin.hantext.aspect.toast;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.HanHelper;

/**
 * @CreateBy Administrator
 * @Date 2017/11/4  11:06
 * @Description
 */

public class T {
    public static final String TAG="T";
    private  static android.widget.Toast toast =null;

    /**
     * Toast  消息弹出
     */
    public static void show(final String message){
        if(TextUtils.isEmpty(message)){
            return;
        }
        //判断是否是在主线程
        if(Looper.getMainLooper().getThread()!=Thread.currentThread()){
            HanHelper.getInstance().getScreenHelper().currentActivity().runOnUiThread(new Runnable() {
                @Override public void run() {
                    showToast(HanHelper.getInstance().getApplication(),message, Toast.LENGTH_SHORT);
                }
            });
        }else{
            showToast(HanHelper.getInstance().getApplication(),message, Toast.LENGTH_SHORT);
        }
        L.i(TAG,"Toast_____________________________"+message);

    }

    /**
     * 弹出提示。
     */
    private static void showToast(Context context,String message,int duration){
        if(toast==null){
                toast= android.widget.Toast.makeText(context,message,duration);
        }else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        toast.show();
    }






}
