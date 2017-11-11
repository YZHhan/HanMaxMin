package com.han.hanmaxmin.common.utils.helper;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  14:10
 * @Description  缓存的工具类
 * 将Model对象序列化到本地的/data/data/<package name>/fileName文件中
 * 或者从file读取内容，转换成Model对象
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
