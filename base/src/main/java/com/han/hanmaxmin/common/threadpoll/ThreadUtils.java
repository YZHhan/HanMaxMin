package com.han.hanmaxmin.common.threadpoll;

import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;

/**
 * @CreateBy Administrator
 * @Date 2017/11/13  11:10
 * @Description
 *
 * ThreadFactory是一个线程工厂，用来创建线程。卫
 *
 * 使用ThreadFactory来创建线程。
 * 通常使用两种方式创建线程：
 *          1.线程实现Runnable接口的类和扩展
 *          2.ThreadFactory接口，创建你自己的Thread对象工厂。
 *
 *           我们通常要使用线程来执行任务，任务通过Runnable定义，然后new 一个Thread来进行执行。
 *           假如我们要改变一个Thread的名称，那么我们可以在new Thread的时候设置名称
 *           那么如果我们需要对多个线程 都改名，难道每次new都要定义吗？
 *           实际上我们可以通过ThreadFactory来专门生成Thread，对于Thread的属性我们可以在其中进行统一定义
 *
 *
 */
class ThreadUtils {

    /**
     * 默认线程创建形式
     *
     * @param name 名称
     * @param deam true：表示守护线程，false 表示用户线程
     *         Explain（守护线程—主线程销毁也跟着销毁，用户线程—主线程销毁不会跟着销毁）
     * @return
     */
     static ThreadFactory generateThread(final String name,final boolean deam ){
         return new ThreadFactory() {
             @Override public Thread newThread(@NonNull Runnable runnable) {
                 CustomThread result=new CustomThread(runnable,name);
                 result.setDaemon(deam);
                 return result;
             }
         };
     }
}
