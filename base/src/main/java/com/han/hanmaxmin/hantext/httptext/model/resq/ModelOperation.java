package com.han.hanmaxmin.hantext.httptext.model.resq;

import com.han.hanmaxmin.common.model.HanModel;
import com.han.hanmaxmin.hantext.httptext.model.BaseModel;

import java.util.List;

/**
 * Created by ptxy on 2018/3/11.
 */

public class ModelOperation extends BaseModel{
    public BannerInfo banner;
    public IconInfo   icon1;
    public IconInfo   icon2;

    public static class BannerInfo extends BaseModel {
        public boolean           onOff;
        public int               time;
        public List<ImagesModel> images;
    }

    public static class ImagesModel extends BaseModel {
        public String regionOption;
        public String id;
        public String plate;
        public String type;
        public Object region;
        public String regionId;
        public String startTime;
        public String endTime;
        public String deeplinkType;
        public String deeplinkURL;
        public String imageURL;
        public int    sortNum;
        public String description;
        public int    createrId;
        public int    lastOperatorId;
        public String createdAt;
        public String updatedAt;
    }

    public static class IconInfo extends BaseModel {
        public boolean onOff;
        public String  imageUrl;
        public String  deeplinkType;
        public String  deeplinkURL;
        public String  regionOption;
        public String  id;
        public String  plate;
        public String  type;
        public Object  region;
        public String  regionId;
        public String  startTime;
        public String  endTime;
        public String  imageURL;
        public int     sortNum;
        public String  description;
        public int     createrId;
        public int     lastOperatorId;
        public String  createdAt;
        public String  updatedAt;
    }

}
