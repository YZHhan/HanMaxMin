package com.han.hanmaxmin.activity;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.aspect.permission.Permission;
import com.han.hanmaxmin.common.utils.HanHelper;

public class MainActivity extends AppCompatActivity {
    private TextView main_text;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HanHelper.getInstance().getScreenHelper().pushActivity(this);
        main_text = (TextView) findViewById(R.id.main_text);
        main_text.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                toast();
            }
        });




    }

    @Permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private void toast() {
        Toast.makeText(MainActivity.this, "q", Toast.LENGTH_SHORT).show();
    }


}
