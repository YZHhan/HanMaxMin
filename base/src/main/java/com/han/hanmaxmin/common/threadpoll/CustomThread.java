package com.han.hanmaxmin.common.threadpoll;

import android.os.Process;

/**
 * @CreateBy Administrator
 * @Date 2017/11/13  12:02
 * @Description
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
