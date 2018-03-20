package com.han.hanmaxmin.mvp.adapter.pageradapteranim;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.han.hanmaxmin.common.log.L;

/**
 * Created by ptxy on 2018/3/20.
 * ViewPager页面切换的动画。
 * 实现翻页动画的关键就是重写transformPage方法，方法里有两个参数view和position,理解这两个参数非常重要。假设有三个页面view1，view2，view3从左至右在viewPager中显示

 往左滑动时：view1，view2，view3的position都是不断变小的。
 view1的position: 0 → -1 → 负无穷大
 view2的position: 1 → 0 → -1
 view3的position: 1 → 0
 往右滑动时：view1，view2，view3的position都是不断变大的。
 view1的position: -1 → 0
 view2的position: -1 → 0 → 1
 view3的position: 0 → 1→ 正无穷大
 当position是正负无穷大时view就离开屏幕视野了。因此最核心的控制逻辑是在[-1,0]和(0,1]这两个区间，通过设置透明度，平移，旋转，缩放等动画组合可以实现各式各样的页面变化效果。

 作者：西瓜太郎123
 链接：https://www.jianshu.com/p/e5abbda4a71c
 來源：简书
 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */

public class AnimPagerTransformer implements ViewPager.PageTransformer{

    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View view, float position) {
        L.d("DepthPageTransformer", view.getTag() + " , " + position + "");
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
