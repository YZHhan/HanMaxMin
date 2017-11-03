package com.han.hanmaxmin.common.utils.helper;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  14:10
 * @Description
 */

public class CacheHelper {
    private static final String TAG="CacheHelper";
    private static CacheHelper helper;
    private CacheHelper() {
    }

    public static CacheHelper getInstance(){
        if (helper==null){
            synchronized (CacheHelper.class){
                if(helper==null)helper=new CacheHelper();
            }
        }
        return helper;
    }
}
