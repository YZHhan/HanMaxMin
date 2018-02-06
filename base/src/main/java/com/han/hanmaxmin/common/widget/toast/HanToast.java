package com.han.hanmaxmin.common.widget.toast;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import com.han.hanmaxmin.common.aspect.thread.ThreadPoint;
import com.han.hanmaxmin.common.utils.HanHelper;
import com.han.hanmaxmin.hantext.aspect.toast.Toast;

/**
 * Created by ptxy on 2018/2/5.
 */

public class HanToast {
    private static android.widget.Toast mToast = null;

    public static void show(final String msg){
        if(TextUtils.isEmpty(msg)){
            return;
        }

        //判断是否在主线程
        if(Looper.getMainLooper().getThread() != Thread.currentThread()){
            HanHelper.getInstance().getThreadHelper().getMainThread().execute(new Runnable() {
                @Override
                public void run() {
                    showToast(HanHelper.getInstance().getApplication(), msg, android.widget.Toast.LENGTH_SHORT);
                }
            });
        } else {
            showToast(HanHelper.getInstance().getApplication(), msg, android.widget.Toast.LENGTH_SHORT);
        }

    }
    /**
     * 弹出提示
     */

    private static void showToast(Context context, String text, int duration){
        if(mToast == null){
            mToast = android.widget.Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

}
