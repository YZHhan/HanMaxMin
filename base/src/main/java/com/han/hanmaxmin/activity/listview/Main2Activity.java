package com.han.hanmaxmin.activity.listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.activity.listview.list.ListViewAdapter;
import com.han.hanmaxmin.activity.listview.recycler.RecyclerActivity;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private ListView listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        listView= (ListView) findViewById(R.id.actiivty2_listView);
        List<String > list=new ArrayList<>();
        for(int i =0;i<40;i++){
            list.add("是"+i);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Main2Activity.this, RecyclerActivity.class);
                startActivity(intent);
                Toast.makeText(Main2Activity.this, "nimei", Toast.LENGTH_SHORT).show();
            }
        });
        listView.setAdapter(new ListViewAdapter(list,this));

    }
}
