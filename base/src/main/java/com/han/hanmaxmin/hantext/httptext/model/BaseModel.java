package com.han.hanmaxmin.hantext.httptext.model;

import com.han.hanmaxmin.common.model.HanModel;

/**
 * Created by ptxy on 2018/2/27.
 */

public class BaseModel extends HanModel {
    public String code;
    public String message;

    public boolean isSuccess(){
        return true;
    }
}
