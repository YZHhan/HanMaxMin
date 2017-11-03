package com.han.hanmaxmin.common.utils.helper;

/**
 * @CreateBy Administrator
 * @Date 2017/11/3  14:10
 * @Description
 */

public class ImageHelper {
    private static final String TAG="ImageHelper";
    private static ImageHelper helper;
    private ImageHelper() {
    }

    public static ImageHelper getInstance(){
        if (helper==null){
            synchronized (ImageHelper.class){
                if(helper==null)helper=new ImageHelper();
            }
        }
        return helper;
    }
}
