package com.han.hanmaxmin.common.threadpoll;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * @CreateBy Administrator
 * @Date 2017/11/13  10:30
 * @Description  执行器
 * Executor 接口 ，很简单，只有一个execute回调接口。、
 * 通常不用来创建线程，重点是执行；
 * 没有严格要求任务的执行和创建是异步还是同步；
 * 实现类：
 * ExecutorService：
 *    void shutdown()：执行完之前提交的任务后终止
 *     List<Runnable> shutdownNow()：立刻终止
 *
 *
 *          execute: //在未来某个时间执行任务，根据实现类的不同有不同的执行情况
 *
 * 作用：可以将任务的提交与执行分离开来
 *
 *
 *
 *
 */

public class MainExecutor implements Executor{
    private static MainExecutor executor;
    public static MainExecutor getInstance(){
        if(executor==null){
            synchronized (MainExecutor.class){
                if(executor==null){
                    executor=new MainExecutor();
                }
            }
        }
        return executor;
    }

    private final Handler handler=new Handler(Looper.getMainLooper()) ;


    @Override public void execute(@NonNull Runnable command) {
        handler.post(command);
    }
}
