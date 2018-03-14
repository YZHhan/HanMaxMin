package com.han.hanmaxmin.hantext.httptext.model;

import com.han.hanmaxmin.common.model.HanModel;

/**
 * Created by ptxy on 2018/3/11.
 * HTTP 请求基类  Body
 */

public class BaseModelReq extends HanModel{

    public ModelPageInfo pageInfo;

    /**
     * 列表分页需要的数据Model
     */
    public static class ModelPageInfo extends HanModel {
        public int pageNum;
        public int pageSize;

        public ModelPageInfo(int pageNum) {
            this(pageNum,10);
        }

        public ModelPageInfo(int pageNum, int pageSize) {
            this.pageNum = pageNum;
            this.pageSize= pageSize;
        }
    }
}
