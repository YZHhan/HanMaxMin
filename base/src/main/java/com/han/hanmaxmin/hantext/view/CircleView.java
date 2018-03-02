package com.han.hanmaxmin.hantext.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.util.Measure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.han.hanmaxmin.common.log.L;

/**
 * Created by ptxy on 2018/2/28.
 * 自定义View    。。。
 * ViewRoot 对应于ViewRootImpl类，它是连接WindowManager和DecorView的纽带。View的三大流程（绘制。测量和布局）
 * 均是通过ViewRoot来完成。
 * <p>
 * <p>
 * 1.继承View
 * 当我们需要实现的效果是一个不规则效果的时候，这是就需要继承View了，需要重写onDraw方法，
 * 在改方法里实现各种不同的图形和效果。用这种方法，需要自己的处理，Wrape_content 和padding。
 * 2.继承ViewGroup
 * 当系统提供的LinearLayout、FrameLayout等布局控件无法满足我们的这个需求时，这是我们就需要
 * 这种方式来实现自己想要的布局效果了。重写onLayout方法对子View进行布局，以及测量本身和子View
 * 宽高，还需要处理本身的padding和子View的margin。
 * 3.继承已有的View
 * 对已有的View进行扩展和修改时，比如需要一个圆角的ImageVIew，那么这时可以继承ImageView进行
 * 修改了，这种方式一般不需要自己去处理wrape_content、padding、
 * 4.继承已有的布局
 * 也叫自定义组合View，改方式比较简单，我们需要将一组View组合在一起，
 */

public class CircleView extends View {

    private Paint mPaint;
    private float mRadius = 50;//园的半径


    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();//初始化画笔
        mPaint.setColor(Color.BLUE);//设置画笔的颜色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //  getWidth  和getHeight  得到的是View的最终大小。
        int width = getWidth();
        L.i("CircleView", "getWidth :" + width);//1080
        int height = getHeight();
        L.i("CircleView", "getHeight :" + height);//300

        int measuredWidth = getMeasuredWidth();
        L.i("CircleView", "getMeasuredWidth :" + measuredWidth);//1080
        int measuredHeight = getMeasuredHeight();
        L.i("CircleView", "getMeasuredHeight :" + measuredHeight);//300

        //加上padding值，，
        int paddingTop = getPaddingTop();
        L.i("CircleView", "getPaddingTop :" + paddingTop);//
        int paddingBottom = getPaddingBottom();
        L.i("CircleView", "getPaddingBottom :" + paddingBottom);//
        int paddingLeft = getPaddingLeft();
        L.i("CircleView", "getPaddingLeft :" + paddingLeft);//
        int paddingRight = getPaddingRight();
        L.i("CircleView", "getPaddingRight :" + paddingRight);//

        int circleWidth = getWidth() - paddingLeft - paddingRight;
        int circleHeoght = getHeight() - paddingBottom - paddingTop;
        canvas.drawCircle(circleWidth / 2 + paddingLeft, circleHeoght / 2 + paddingTop, mRadius, mPaint);


//        canvas.drawCircle(width/2, height/2, mRadius, mPaint);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthModel == MeasureSpec.AT_MOST) {
            widthSize = (int) (mRadius * 2 + getPaddingLeft() + getPaddingRight());
        }

        if (heightModel == MeasureSpec.AT_MOST){
            heightSize = (int) (mRadius * 2 + getPaddingBottom() + getPaddingTop());
        }

        setMeasuredDimension(widthSize, heightSize);
    }
}
