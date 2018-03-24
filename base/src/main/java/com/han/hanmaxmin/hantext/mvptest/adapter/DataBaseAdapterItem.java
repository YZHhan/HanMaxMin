package com.han.hanmaxmin.hantext.mvptest.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.greendao.model.UserInfo;
import com.han.hanmaxmin.common.viewbind.annotation.Bind;
import com.han.hanmaxmin.hantext.mvptest.model.ModelUserInfo;
import com.han.hanmaxmin.mvp.adapter.HanListAdapterItem;
import com.han.hanmaxmin.mvp.adapter.HanRecyclerAdapterItem;

/**
 * Created by ptxy on 2018/3/22.
 */

public class DataBaseAdapterItem extends HanRecyclerAdapterItem<UserInfo> {
    @Bind(R.id.tv_name) TextView tv_name;
    @Bind(R.id.tv_tagType1) TextView tv_tagType1;
    @Bind(R.id.tv_tagType2) TextView tv_tagType2;
    @Bind(R.id.tv_age) TextView tv_age;
    @Bind(R.id.tv_like) TextView tv_like;
    @Bind(R.id.tv_number) TextView tv_number;

    public DataBaseAdapterItem(LayoutInflater inflater, ViewGroup parent) {
        super(inflater, parent);
    }

    @Override
    protected int itemViewLayoutId() {
        return R.layout.data_base_item;
    }

    @Override
    protected void onBindItemData(UserInfo info, int position, int totalCount) {
        tv_name.setText(info.getName());
        tv_tagType1.setText(info.getTeacher_tag_t1());
        tv_age.setText(info.getAge());
        tv_like.setText(info.getLike());
        tv_number.setText(info.getNumber());
        tv_tagType2.setText(info.getTeacher_tag_t4());
    }
}
