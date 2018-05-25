package com.han.hanmaxmin.mvp.common;

import com.han.hanmaxmin.common.config.HanProperties;
import com.han.hanmaxmin.common.config.Property;

import java.security.PublicKey;
import java.util.List;

/**
 * Created by ptxy on 2018/4/4.
 * 当被volatile修饰的话，有两种含义
 * 1.保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
 * 2.禁止进行指令重排序。
 */

public class UserConfig extends HanProperties {

    private static volatile UserConfig USER_CONFIG;

    @Override
    public String initTag() {
        return getClass().getSimpleName();
    }

    private UserConfig() {
    }

    public UserConfig(String configName) {
        super(configName);
    }

    @Override
    public int initType() {
        return HanProperties.OPEN_TYPE_DATA;
    }

    public static UserConfig getInstance(){
        if(USER_CONFIG == null){
            synchronized (UserConfig.class){
                if(USER_CONFIG == null){
                    USER_CONFIG = new UserConfig("UserConfig_" + "Yinzihan");
                }
            }
        }
        return USER_CONFIG;
    }


    @Property public String UserName;
    @Property public String UserPhone;
    @Property public String UserAge;
    @Property public String UserSex;
    @Property public String UserHeight;
    @Property public String UserLike;
    @Property public List<String> UserVipList;




}
