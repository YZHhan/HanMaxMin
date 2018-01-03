package com.han.hanmaxmin.common.viewbind;

import java.util.HashSet;

/**
 * @CreateBy Administrator
 * @Date 2017/12/25  11:45
 * @Description
 */

final class EventListenerManager {
    public final static long QUICK_EVENT_TIME_SPAN = 300;
    public final static HashSet<String> AVOID_QUICK_EVENT_SET=new HashSet<>(2);

    static {
            AVOID_QUICK_EVENT_SET.add("onClick");
            AVOID_QUICK_EVENT_SET.add("onItemClick");
    }

    private EventListenerManager() {
    }


}
