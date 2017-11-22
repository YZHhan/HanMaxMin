package com.han.hanmaxmin.common.threadpoll;

import com.han.hanmaxmin.common.constants.HanConstants;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @CreateBy Administrator
 * @Date 2017/11/13  10:25
 * @Description  Androdi中的线程池。
 *          1. 重用线程池中线程，避免因为创建线程的创建和销毁带来的消耗
 *          2.有效的控制线程池的最大并发数，避免大量的线程之间因互相抢占系统资源而导致的阻塞现象
 *          3.能够对线程进行简单的管理，并提供定时执行，以及指定间隔循环执行。
 *
 *
 *      ThreadPollExecution：
 *         a>corePoolSize:线程池的核心线程数
 *         b>maximumPoolSize:线程池所能容纳的最大线程数
 *         c>keepAliveTime:非核心线程闲置时的的超时策略
 *         d>TimeUnit  unit:值keepAliveTime参数的单位时间
 *         e>BlockQueue<Runnable> workQueue:线程池的任务队列，通过线程池的execute方法提交的Runnable对象会处储存在这个空间里
 *         f>threadFactory:线程工厂，为线程提供创建新线程的功能
 *
 *      SingleThreadPoll:单例线程池
 *      核心线程数为零，最大线程数为1，时间为60毫秒的守护线程
 *
 *
 */

public class SingleThreadPoll extends ThreadPoolExecutor{

    /**单例线程
     *  无核心线程 ，最大线程数为1，超时机制为60毫秒。（同一所有的外界任务到一个线程中）
     *
     */
     SingleThreadPoll() {
        super(0, 1, 60, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<Runnable>(),ThreadUtils.generateThread(HanConstants.NAME_SINGLE_THREAD,true));
    }
}
