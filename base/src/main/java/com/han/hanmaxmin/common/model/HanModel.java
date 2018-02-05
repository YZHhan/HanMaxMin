package com.han.hanmaxmin.common.model;

/**
 * Created by ptxy on 2018/2/2.
 *
 * 网路请求的基类。。
 */

public class HanModel {
    public HanModel() {

    }

    /**
     * Http 是否请求成功，有子类实现。
     */
    public boolean isResponseOK(){return true;}


    /**
     * 列表分页是否是最后一页，有子类实现
     */
    public boolean isLastPage(){return false;}


    /**
     * 获取网路请求信息，有子类实现
     */
    public String getMessage(){return null;}






}
