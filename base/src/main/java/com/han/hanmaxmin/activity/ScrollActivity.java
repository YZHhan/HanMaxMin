package com.han.hanmaxmin.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.widget.scrollview.MyScrollView;
import com.han.hanmaxmin.mvp.HanActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptxy on 2018/3/7.
 */

public class ScrollActivity extends HanActivity implements MyScrollView.OnScrollListener {
    private MyScrollView  myScrollView;
    private List<String> data;
    private TextView top;
    private boolean hasShow  = true;
    private ValueAnimator valueAnimator;

    @Override
    public int layoutId() {
        return R.layout.activity_main4;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initData();
        myScrollView = (MyScrollView) findViewById(R.id.scrollView);
        myScrollView.setOnScrollListener(this);
        top = (TextView) findViewById(R.id.top);
    }

    private void initData() {
        data=new ArrayList<String>();
        for (int i=0;i<40;i++){
            data.add("Data-"+i);
        }
    }

    @Override
    public boolean isShowBackButtonInDefaultView() {
        return false;
    }

    @Override
    public void onScroll(int scrollY) {

        if(scrollY < top.getMeasuredHeight()){
            scrollY = 0;
        } else {
            scrollY = top.getMeasuredHeight() - scrollY;
            if(scrollY < -125){
                scrollY = -125;
            }
        }
        L.i("onScroll ","onScroll   Y  ===="  +  scrollY);
        top.setTranslationY(scrollY);
        if(scrollY == -125){
//            top.setSystemUiVisibility(View.GONE);
        }
        disPlayAlphaAnimation(top, scrollY== 0);

    }

    private void disPlayAlphaAnimation(final View view, boolean show) {
        if(show != hasShow){
            hasShow =show;
            if(valueAnimator == null){
                valueAnimator = ValueAnimator.ofFloat(1f, 0f).setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        if (view != null) view.setAlpha(value);
                    }
                });
            }
            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.setFloatValues(hasShow ? 0f : 1f, hasShow ? 1f : 0f);
            valueAnimator.start();
        }

    }
}
