package com.han.hanmaxmin.mvp.adapter;

import android.view.View;

import com.han.hanmaxmin.common.utils.HanHelper;

/**
 * Created by ptxy on 2018/3/8.
 * 适配器
 *
 */

public abstract class HanListAdapterItem<T> {

    protected String initTag(){return getClass().getSimpleName();}

    public abstract int getItemLayout();

    public void init(View contentView){
//        HanHelper.getInstance().//bind

    }

    public abstract void bindData(T t, int position, int count);


}
