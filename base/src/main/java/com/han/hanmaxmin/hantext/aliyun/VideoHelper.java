package com.han.hanmaxmin.hantext.aliyun;

import android.app.Application;

import com.alivc.player.AliVcMediaPlayer;

/**
 * @CreateBy Administrator
 * @Date 2017/11/20  18:27
 * @Description
 */

public class VideoHelper {
    private static VideoHelper videoHelper= new VideoHelper();

    private VideoHelper() {
        super();
    }

    public static VideoHelper getInstance(){
        return videoHelper;
    }

    public void init(Application application,String businessId ){
        AliVcMediaPlayer.init(application,businessId);
    }


}
