package com.han.hanmaxmin.common.widget.ptr.utils;

import android.util.Log;

/**
 * Created by ptxy on 2018/3/12.
 * An encapsulation of{@link android.util.Log},enable log level and print log with parameters. (使用参数启用日志级别和打印日志。)
 *
 *
 *
 */

public class PtrCLog {

    private static final int LEVEL_VERBOSE = 0;
    private static final int LEVEL_DEBUG   = 1;
    private static final int LEVEL_INFO    = 2;
    private static final int LEVEL_WARNING = 3;
    private static final int LEVEL_ERROR   = 4;
    private static final int LEVEL_FATAL   = 5;

    private static int sLevel = LEVEL_VERBOSE;

    /**
     * set log level, the level lower than this level will not be logged
     * 设置日志级别，低于此级别的级别将不会被记录。
     */
    public static void setLogLevel(int level){
        sLevel = level;
    }

    /**
     * Send a VERBOSE log message.
     */
    public static void v(String tag, String msg){
        if(sLevel <LEVEL_VERBOSE){
            return;
        }
        Log.v(tag, msg);
    }

    /**
     * Send a VERBOSE log message.
     */
    public static void v(String tag, String msg, Throwable throwable) {
        if (sLevel > LEVEL_VERBOSE) {
            return;
        }
        Log.v(tag, msg, throwable);
    }

    /**
     * Send a VERBOSE log message.
     */
    public static void v(String tag, String msg, Object... args) {
        if (sLevel > LEVEL_VERBOSE) {
            return;
        }
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        Log.v(tag, msg);
    }

    /**
     * Send a DEBUG log message
     */
    public static void d(String tag, String msg) {
        if (sLevel > LEVEL_DEBUG) {
            return;
        }
        Log.d(tag, msg);
    }

    /**
     * Send a DEBUG log message
     */
    public static void d(String tag, String msg, Object... args) {
        if (sLevel > LEVEL_DEBUG) {
            return;
        }
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        Log.d(tag, msg);
    }

    /**
     * Send a DEBUG log message
     */
    public static void d(String tag, String msg, Throwable throwable) {
        if (sLevel > LEVEL_DEBUG) {
            return;
        }
        Log.d(tag, msg, throwable);
    }

    /**
     * Send an INFO log message
     */
    public static void i(String tag, String msg) {
        if (sLevel > LEVEL_INFO) {
            return;
        }
        Log.i(tag, msg);
    }

    /**
     * Send an INFO log message
     */
    public static void i(String tag, String msg, Object... args) {
        if (sLevel > LEVEL_INFO) {
            return;
        }
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        Log.i(tag, msg);
    }

    /**
     * Send an INFO log message
     */
    public static void i(String tag, String msg, Throwable throwable) {
        if (sLevel > LEVEL_INFO) {
            return;
        }
        Log.i(tag, msg, throwable);
    }

    /**
     * Send a WARNING log message
     */
    public static void w(String tag, String msg) {
        if (sLevel > LEVEL_WARNING) {
            return;
        }
        Log.w(tag, msg);
    }

    /**
     * Send a WARNING log message
     */
    public static void w(String tag, String msg, Object... args) {
        if (sLevel > LEVEL_WARNING) {
            return;
        }
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        Log.w(tag, msg);
    }

    /**
     * Send a WARNING log message
     */
    public static void w(String tag, String msg, Throwable throwable) {
        if (sLevel > LEVEL_WARNING) {
            return;
        }
        Log.w(tag, msg, throwable);
    }

    /**
     * Send an ERROR log message
     */
    public static void e(String tag, String msg) {
        if (sLevel > LEVEL_ERROR) {
            return;
        }
        Log.e(tag, msg);
    }

    /**
     * Send an ERROR log message
     */
    public static void e(String tag, String msg, Object... args) {
        if (sLevel > LEVEL_ERROR) {
            return;
        }
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        Log.e(tag, msg);
    }

    /**
     * Send an ERROR log message
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if (sLevel > LEVEL_ERROR) {
            return;
        }
        Log.e(tag, msg, throwable);
    }

    /**
     * Send a FATAL ERROR log message
     */
    public static void f(String tag, String msg) {
        if (sLevel > LEVEL_FATAL) {
            return;
        }
        Log.wtf(tag, msg);
    }

    /**
     * Send a FATAL ERROR log message
     */
    public static void f(String tag, String msg, Object... args) {
        if (sLevel > LEVEL_FATAL) {
            return;
        }
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        Log.wtf(tag, msg);
    }

    /**
     * Send a FATAL ERROR log message
     */
    public static void f(String tag, String msg, Throwable throwable) {
        if (sLevel > LEVEL_FATAL) {
            return;
        }
        Log.wtf(tag, msg, throwable);
    }




}
