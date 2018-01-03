package com.han.hanmaxmin.hantext.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.han.hanmaxmin.R;

/**
 * @CreateBy Administrator
 * @Date 2017/12/24  11:49
 * @Description
 */

public class ImageSwitcherActivity extends FragmentActivity {
    private ImageSwitcher imageSwitcher;
    private int           index;
    private int[] array = {R.drawable.iv_madiaview_question, R.drawable.iv_mediaview_topic, R.drawable.tv_mediaview_peiyou};

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageswitcher_layout);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.is_image);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override public View makeView() {
                return new ImageView(ImageSwitcherActivity.this);
            }
        });


        imageSwitcher.setImageResource(array[index]);

        imageSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                index++;
                if (index > array.length) {
                    index = 0;
                }
                imageSwitcher.setImageResource(array[index]);
            }
        });

        //设置切入动画
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left));
        //设置切出动画
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_out_right));


    }
}
