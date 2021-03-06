package com.han.hanmaxmin.common.widget.refreshHeader;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.han.hanmaxmin.common.log.L;
import com.han.hanmaxmin.common.utils.CommonUtils;
import com.han.hanmaxmin.common.widget.ptr.PtrFrameLayout;
import com.han.hanmaxmin.common.widget.ptr.PtrUIHandler;
import com.han.hanmaxmin.common.widget.ptr.indicator.PtrIndicator;

/**
 * Created by ptxy on 2018/3/16.
 * PtrUIHandler  下拉刷新的UI接口（）
 * 准备下拉，下拉中，下拉完成，重置，下拉过程位置变化
 */

public class BeautyCircleRefreshHeader extends RelativeLayout implements PtrUIHandler {

    private static final int HEADER_HEIGHT = CommonUtils.dp2px(50);
    private ImageView ivRefresh;
    private BeautyCircleDrawable circleLogoDrawable;


    public BeautyCircleRefreshHeader(Context context) {
        super(context);
    }

    public BeautyCircleRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BeautyCircleRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        L.i("HanPullRecyclerFragment","我是BeautyCircleRefreshHeader的init方法");
        setGravity(Gravity.CENTER);
        addView(getHeaderView());
    }

    private View getHeaderView() {
        L.i("HanPullRecyclerFragment","我是BeautyCircleRefreshHeader的getHeaderView方法");
        if (ivRefresh == null) {
            ivRefresh = new ImageView(getContext());
            ivRefresh.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            circleLogoDrawable = new BeautyCircleDrawable();
            ivRefresh.setBackgroundDrawable(circleLogoDrawable);
            LayoutParams imageParams = new LayoutParams(CommonUtils.dp2px(35), CommonUtils.dp2px(35));
            imageParams.addRule(CENTER_IN_PARENT, TRUE);
            ivRefresh.setLayoutParams(imageParams);
        }
        RelativeLayout headerLayout = new RelativeLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(HEADER_HEIGHT, HEADER_HEIGHT);
        headerLayout.setLayoutParams(layoutParams);
        headerLayout.addView(ivRefresh);
        return headerLayout;
    }

    public void setPtrFrameLayout(PtrFrameLayout layout) {
        layout.setOffsetToKeepHeaderWhileLoading((int) CommonUtils.dp2px(getResources(), 80));
    }

    /**
     * 准备刷新
     */
    @Override public void onUIRefreshPrepare(PtrFrameLayout ptrFrameLayout) {
        L.i("BeautyCircleRefreshHeader", "我是准备刷新");
        if (circleLogoDrawable != null) {
            circleLogoDrawable.onPrepare();
        }
    }

    /**
     * 开始刷新
     */
    @Override public void onUIRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        L.i("BeautyCircleRefreshHeader", "我是开始刷新");
        if (circleLogoDrawable != null) {
            circleLogoDrawable.onBegin();
        }
    }

    /**
     * 刷新完成
     */
    @Override public void onUIRefreshComplete(PtrFrameLayout ptrFrameLayout) {
        L.i("BeautyCircleRefreshHeader", "我是刷新完成");

        if (circleLogoDrawable != null) {
            circleLogoDrawable.onRefreshComplete();
        }
    }

    /**
     * 刷新重置
     */
    @Override public void onUIReset(PtrFrameLayout ptrFrameLayout) {
        L.i("BeautyCircleRefreshHeader", "我是刷新充值");
        if (circleLogoDrawable != null) {
            circleLogoDrawable.onReset();
        }
    }


    /**
     * 当下拉高度高于设定临界点时改变刷新头状态
     */
    @Override public void onUIPositionChange(PtrFrameLayout ptrFrameLayout, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        L.i("HanPullRecyclerFragment","我是BeautyCircleRefreshHeader ptrIndicator.getRatioOfHeaderToHeightRefresh()" +ptrIndicator.getRatioOfHeaderToHeightRefresh() + "    ptrIndicator.getCurrentPercent()"  +ptrIndicator.getCurrentPercent()  );
        float percent = Math.min(ptrIndicator.getRatioOfHeaderToHeightRefresh(), ptrIndicator.getCurrentPercent());
        if (checkCanAnimation()) {
            circleLogoDrawable.setPercent(percent);
            circleLogoDrawable.setIsReachCriticalPoint(percent == ptrIndicator.getRatioOfHeaderToHeightRefresh());
            ViewCompat.setTranslationY(ivRefresh, (1f - percent) * HEADER_HEIGHT / 2);
        }
    }

    private boolean checkCanAnimation() {
        return circleLogoDrawable != null && ivRefresh != null;
    }

}
