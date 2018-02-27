package com.han.hanmaxmin.common.utils.stream;

import java.io.Closeable;
import java.io.IOException;

/**
 * @CreateBy qsmaxmin
 * @Date 2017/6/29 17:43
 * @Description  关流工具类
 */

public class StreamCloseUtils {

    public static void close(Closeable... closeable){
        if(closeable != null){
            for (Closeable able : closeable){
                if(able != null){
                    try {
                        able.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
