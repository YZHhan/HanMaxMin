package com.han.hanmaxmin.common.threadpoll;

import com.han.hanmaxmin.common.constants.HanConstants;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @CreateBy Administrator
 * @Date 2017/11/13  10:24
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
 *
 *         WorkThreadPoll：
 *         非核心线程的存活时间是20秒 的守护线程
 *
 *         在封装中，HttpThreadPoll(线程数量固定的线程池，只有核心线程，无超时机制。（能够更快的响应外界的请求）)
 *                  WorkThreadPoll（线程数量固定的线程池，只要核心线程数，超时机制为20秒）
 *                  SingleThreadPoll（无核心线程 ，最大线程数为1，超时机制为60毫秒。（同一所有的外界任务到一个线程中））
 *
 *
 *
 */

public class WorkThreadPoll extends ThreadPoolExecutor{

    /** 工作线程
     *
     * 线程数量固定的线程池，只要核心线程数，超时机制为20秒。（）
     */
    WorkThreadPoll(int threadCount){
         super(threadCount,threadCount,20, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(),ThreadUtils.generateThread(HanConstants.NAME_WORK_THREAD,true));
    }
}
