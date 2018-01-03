package com.han.hanmaxmin.activity.viewpager;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.han.hanmaxmin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreateBy Administrator
 * @Date 2017/11/23  11:16
 * @Description
 */

public class ViewPagerActivity extends FragmentActivity {
    private View view1, view2, view3;
    private ViewPager viewPager;
    private List<View> viewList;



    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        viewPager= (ViewPager) findViewById(R.id.vp_veiwpager);
        LayoutInflater inflater=LayoutInflater.from(this);

        view1=inflater.inflate(R.layout.viewpager_1,null);
        view2=inflater.inflate(R.layout.viewpager_2,null);
        view3=inflater.inflate(R.layout.viewpager_3,null);

        viewList=new ArrayList<>();

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        PagerAdapter pagerAdapter=new PagerAdapter() {
            @Override public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }


            /**
             * 为给定的位置移除相应的View。
             *
             * @param container ViewPager本身
             * @param position  给定的位置
             * @param object    在instantiateItem中提交给ViewPager进行保存的实例对象
             */
            @Override public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager)container).removeView(viewList.get(position % viewList.size()));
            }

            /**
             * 为给定的位置创建相应的View。创建View之后,需要在该方法中自行添加到container中。
             *
             * @param container ViewPager本身
             * @param position  给定的位置
             * @return 提交给ViewPager进行保存的实例对象
             */
            @Override public Object instantiateItem(ViewGroup container, int position) {
                View view = viewList.get(position % viewList.size());
                ((ViewPager)container).addView(view, 0);
                return view;
            }


        };

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(viewList.size() * 100);
    }
}
