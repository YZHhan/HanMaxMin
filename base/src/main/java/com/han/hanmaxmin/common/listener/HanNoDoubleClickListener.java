package com.han.hanmaxmin.common.listener;

import android.view.View;

import java.util.Calendar;

/**
 * Created by ptxy on 2018/2/9.
 * 解决按钮二次点击问题。
 */

public abstract class HanNoDoubleClickListener implements View.OnClickListener {

    private static final int MIN_CLICK_DELAY_TIME = 1000;//间隔时间
    private long lastClickTime = 0;
    @Override
    public void onClick(View v) {
     long currentTime = Calendar.getInstance().getTimeInMillis();
     if(currentTime - lastClickTime > MIN_CLICK_DELAY_TIME){
     lastClickTime = currentTime;
        onNoDoubleClick(v);
            }
    }

     protected abstract void onNoDoubleClick(View v);
}
