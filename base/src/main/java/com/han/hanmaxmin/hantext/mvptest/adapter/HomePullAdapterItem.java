package com.han.hanmaxmin.hantext.mvptest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.hantext.httptext.model.resq.ModelApp;
import com.han.hanmaxmin.mvp.adapter.HanListAdapterItem;
import com.han.hanmaxmin.mvp.adapter.HanRecyclerAdapterItem;

import org.w3c.dom.Text;

/**
 * Created by ptxy on 2018/3/16.
 */

public class HomePullAdapterItem extends HanListAdapterItem <ModelApp> {
    private TextView jump_textView;

    @Override
    public void init(View contentView) {
        super.init(contentView);
//        jump_textView = (TextView) contentView.findViewById(R.id.jump_text);
    }

    @Override
    public void bindData(ModelApp modelApp, int position, int count) {
//        jump_textView.setText(modelApp.name);
    }

    @Override
    public int getItemLayout() {
        return R.layout.activity_main2;
    }


}
