package com.han.hanmaxmin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.han.hanmaxmin.R;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private ListView listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView= (ListView) findViewById(R.id.actiivty2_listView);
        List<String > list=new ArrayList<>();
        for(int i =0;i<40;i++){
            list.add("是"+i);
        }
        listView.setAdapter(new ListViewAdapter(list,this));
    }
}
