package com.han.hanmaxmin.common.threadpoll;

import com.han.hanmaxmin.common.constants.HanConstants;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @CreateBy Administrator
 * @Date 2017/11/13  10:24
 * @Description  Android的线程池
 *          1. 重用线程池中线程，避免因为创建线程的创建和销毁带来的消耗
 *          2.有效的控制线程池的最大并发数，避免大量的线程之间因互相抢占系统资源而导致的阻塞现象
 *          3.能够对线程进行简单的管理，并提供定时执行，以及指定间隔循环执行。
 *
 *      ThreadPollExecution：
 *         a>corePoolSize:线程池的核心线程数
 *         b>maximumPoolSize:线程池所能容纳的最大线程数
 *         c>keepAliveTime:非核心线程闲置时的的超时策略
 *         d>TimeUnit  unit:值keepAliveTime参数的单位时间
 *         e>BlockQueue<Runnable> workQueue:线程池的任务队列，通过线程池的execute方法提交的Runnable对象会处储存在这个空间里
 *         f>threadFactory:线程工厂，为线程提供创建新线程的功能
 *
 *      HttpThreadPoll：非核心线程的存活时间是0微秒  的守护线程
 *
 *              线程池的分类：四种不同功能特性的线程池，他们都直接或间接的通过配置ThreadFactory来实现自己的功能特性
 *              FixedThreadPool：线程数量固定的线程池，当线程处于空闲状态时，他们并不会被回收，除非线程池被关闭啦。
 *                      当所有的线程都处于活动的状态时 ，新任务都会处于等待状态，知道有线程空闲出来。由于FixedThreadPool
 *                      只有核心线程并且这些核心线程不会被回收，这意味着他能够更加快速的响应外界的请求。只有核心线程数并且
 *                      这个核心线程没有超时机制，另外任务大小也没有限制。
 *              CacheThreadPool：线程数量不定的线程池，他只有非核心线程，并且最大线程数为Integer.MAX_VALUE.当线程池中的线程都处于
 *                     活动状态时，线程池会创建新的线程来处理新任务，否则就会利用空闲线程来处理新任务。线程池中的空闲线程是有超时机制
 *                     ，这个超时机制为60秒
 *
 *              ScheduledThreadPool：
 *              SingleThreadPoo:
 *
 *
 *
 *
 */

public class HttpThreadPoll extends ThreadPoolExecutor {

    /**
     * FixedThreadPoll
     * 线程数量固定的线程池，只有核心线程，无超时机制。（能够更快的响应外界的请求）
     * @param thradCount
     */
     HttpThreadPoll(int thradCount) {
        super(thradCount, thradCount, 0, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<Runnable>(),ThreadUtils.generateThread(HanConstants.NAME_HTTP_THREAD,true));
    }
}
