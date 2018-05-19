package com.example.myapplication;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout container;
    private ViewPager viewpager;
    private Adapter adapter;
    private List<View> viewlist = new ArrayList<View>();

    private int screenWidth = 0;
    private String[] data = { "第一页", "第二页", "第三页", "第四页", "第五页", "第六页" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取屏幕宽度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;


        initData();
        initView();

    }

    private void initData() {
        int size = data.length;
        for (int i = 0; i < size; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.viewitem,
                    null);
            viewlist.add(view);
        }
    }

    private void initView(){

        container = (LinearLayout) this.findViewById(R.id.container);
        viewpager = (ViewPager) this.findViewById(R.id.viewpager);
        viewpager.setOffscreenPageLimit(3); // viewpager缓存页数
        viewpager.setPageMargin(30); // 设置各页面的间距

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                screenWidth / 3, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewpager.setLayoutParams(params);

        adapter = new Adapter(viewlist);

        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(1);

        // 将父节点Layout事件分发给viewpager，否则只能滑动中间的一个view对象
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewpager.dispatchTouchEvent(event);
            }
        });

    }


    public class Adapter extends PagerAdapter{

        private List<View> viewlist;

        public Adapter(List<View> viewlist) {
            this.viewlist = viewlist;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return viewlist.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewlist.get(position), 0);
            return viewlist.get(position);
        }

    }

}
