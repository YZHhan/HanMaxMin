package com.han.hanmaxmin;

/**
 * @CreateBy Administrator
 * @Date 2017/11/13  15:41
 * @Description
 */

public class hhaha {


    /**
     * brandName : 魅族
     * failMsg : // CRASH: com.dyt.grapecollege (pid 29922)
     // Short Msg: java.lang.OutOfMemoryError
     // Long Msg: java.lang.OutOfMemoryError: pthread_create (1040KB stack) failed: Try again
     // Build Label: Meizu/meizu_PRO7S/PRO7S:7.0/NRD90M/1501691279:user/release-keys
     // Build Changelist: 1501691279
     // Build Time: 1501691278000
     // java.lang.OutOfMemoryError: pthread_create (1040KB stack) failed: Try again
     // 	at java.lang.Thread.nativeCreate(Native Method)
     // 	at java.lang.Thread.start(Thread.java:730)
     // 	at java.util.concurrent.ThreadPoolExecutor.addWorker(ThreadPoolExecutor.java:941)
     // 	at java.util.concurrent.ThreadPoolExecutor.ensurePrestart(ThreadPoolExecutor.java:1582)
     // 	at java.util.concurrent.ScheduledThreadPoolExecutor.delayedExecute(ScheduledThreadPoolExecutor.java:313)
     // 	at java.util.concurrent.ScheduledThreadPoolExecutor.scheduleAtFixedRate(ScheduledThreadPoolExecutor.java:575)
     // 	at java.util.concurrent.Executors$DelegatedScheduledExecutorService.scheduleAtFixedRate(Executors.java:658)
     // 	at com.aliyun.vodplayer.b.a.b.b(Unknown Source)
     // 	at com.aliyun.vodplayer.b.a.a.z(Unknown Source)
     // 	at com.aliyun.vodplayer.b.a.a.l(Unknown Source)
     // 	at com.aliyun.vodplayer.b.a.a$4.handleMessage(Unknown Source)
     // 	at android.os.Handler.dispatchMessage(Handler.java:110)
     // 	at android.os.Looper.loop(Looper.java:203)
     // 	at android.os.HandlerThread.run(HandlerThread.java:61)
     //

     * modelName : PRO7 标准版
     * osVersion : 1.2.1
     */

    private String brandName;
    private String failMsg;
    private String modelName;
    private String osVersion;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
}
