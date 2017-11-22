package com.han.hanmaxmin.common.threadpoll;

import com.han.hanmaxmin.common.log.L;

/**
 * @CreateBy Administrator
 * @Date 2017/11/13  10:23
 * @Description 线程池的管理类 对外提供API
 */

public class HanThreadPollHelper {

    private static final String TAG="HanThreadPollHelper";
    private HttpThreadPoll   httpThreadPoll;
    private SingleThreadPoll singleThreadPoll;
    private WorkThreadPoll   workThreadPoll;

    private static HanThreadPollHelper helper;

    private HanThreadPollHelper() {
    }

    public static HanThreadPollHelper getInstance() {
        if (helper == null) {
            synchronized (HanThreadPollHelper.class) {
                if (helper == null) {
                    helper = new HanThreadPollHelper();
                }
            }
        }
        return helper;
    }

    public MainExecutor getMainThread() {return MainExecutor.getInstance();}

    /**
     * 工作线程
     */
    public WorkThreadPoll getWorkThreadPoll(){
        if(workThreadPoll==null){
            synchronized (HanThreadPollHelper.class){
                if(workThreadPoll==null)workThreadPoll=new WorkThreadPoll(10);
            }
        }
        return workThreadPoll;
    }

    /**
     * Http 线程
     * @return
     */
    public HttpThreadPoll getHttpThreadPoll(){
        if(httpThreadPoll==null){
            synchronized (HanThreadPollHelper.class){
                if(httpThreadPoll==null)httpThreadPoll=new HttpThreadPoll(10);
            }
        }
        return httpThreadPoll;
    }

    /**
     * 单例线程
     */
    public SingleThreadPoll getSingleThreadPoll(){
        if(singleThreadPoll==null){
            synchronized (HanThreadPollHelper.class){
                if(singleThreadPoll==null)singleThreadPoll=new SingleThreadPoll();
            }
        }
        return singleThreadPoll;
    }


    /**
     * 执行完之前提交的任务后终止
     */
    public synchronized void shutDown(){
        L.i(TAG,"shutDown");
        if(workThreadPoll!=null){
            workThreadPoll.shutdown();
            workThreadPoll=null;
            L.i(TAG,"At this time,workThradPoll is null");
        }

        if(httpThreadPoll!=null){
            httpThreadPoll.shutdown();
            httpThreadPoll=null;
            L.i(TAG,"At this time,httpThreadPoll is null");
        }

        if(singleThreadPoll!=null){
            singleThreadPoll.shutdown();
            singleThreadPoll=null;
            L.i(TAG,"At this time,singleThreadPoll is null");
        }




    }





}
