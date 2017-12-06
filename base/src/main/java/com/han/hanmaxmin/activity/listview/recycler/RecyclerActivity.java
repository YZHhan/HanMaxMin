package com.han.hanmaxmin.activity.listview.recycler;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.han.hanmaxmin.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @CreateBy Administrator
 * @Date 2017/11/22  21:45
 * @Description RecyclerView 需要导入依赖，才可以。
 * <p>
 * Recycler需要
 * _____________//设置布局管理器
 * _____________//设置adapter
 * _____________//设置Item增加、移除动画
 * _____________//添加分割线
 * _____________//自己设置监听器
 * <p>
 * ````适配器：extends （Recycler.Adapter）----内部类ViewHolder
 * __________onCreateViewHolder
 * __________onBindViewHolder
 * __________getItemCount
 */

public class RecyclerActivity extends FragmentActivity {
    private RecyclerView    recyclerView;
    private List<String>    mDatas;
    private RecyclerAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatas=new ArrayList<>();
        for (int i = 'A'; i < 'Z'; i++) {
            mDatas.add("" + (char) i);
        }
        adapter=new RecyclerAdapter(this,mDatas);
        recyclerView.setAdapter(adapter);

    }
}
