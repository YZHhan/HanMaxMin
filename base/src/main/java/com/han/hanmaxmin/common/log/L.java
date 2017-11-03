package com.han.hanmaxmin.common.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  14:29
 * @Description
 */

public class L {
    private static boolean enable=false;

    private L() {
    }
    /**
     * 控制打印的boolean，通常在Appliacaton里面控制
     */
    public static void init(boolean isLogOpen) {
        enable = isLogOpen;
    }

    public static void v(String tag, String message) {
        println(2, tag, message);
    }

    public static void d(String tag, String message) {
        println(3, tag, message);
    }

    public static void i(String tag, String message) {
        println(4, tag, message);
    }

    public static void w(String tag, String message) {
        println(5, tag, message);
    }

    public static void e(String tag, String message) {
        println(6, tag, message);
    }

    /**
     * Log Println End Source!
     */
    private static void println(int priority, String tag, String message) {
        if (enable && !TextUtils.isEmpty(message)) {
            Log.println(priority, tag, message);
        }
    }

}
