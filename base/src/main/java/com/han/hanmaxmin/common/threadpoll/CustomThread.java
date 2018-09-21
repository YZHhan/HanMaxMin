package com.han.hanmaxmin.common.threadpoll;

import android.os.Process;

/**
 * @CreateBy Administrator
 * @Date 2017/11/13  12:02
 * @Description: 开线程，并且设置线程等级。
 * 采用Android API 来控制线程的优先级。
 * THREAD_PRIORITY_BACKGROUND 后台线程建议设置这个优先级，值为10。
 *
 * 使用Android API 为线程的优先级设置，线程执行时调用android.os.Process.setThreadPriority方法即可
 *
 *
 *
 */

public class CustomThread extends Thread{

    public CustomThread(Runnable runnable,String name) {
        super(runnable,name);
    }

    @Override public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        super.run();
    }
}
