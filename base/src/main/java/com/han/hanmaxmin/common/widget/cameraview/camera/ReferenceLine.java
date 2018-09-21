package com.han.hanmaxmin.common.widget.cameraview.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.han.hanmaxmin.common.widget.cameraview.utils.CameraUtils;

/*
 * @Author yinzh
 * @Date   2018/6/12 19:56
 * @Description:
 *
 */
public class ReferenceLine extends View {
    private Paint mLinePaint;

    public ReferenceLine(Context context) {
        super(context);
        init();
    }

    public ReferenceLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReferenceLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);//设置抗锯齿
        mLinePaint.setColor(Color.parseColor("#FFFFFF"));
        mLinePaint.setStrokeWidth(1);//画笔样式为空心时，设置空心画笔的宽度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int screenWidth = CameraUtils.getScreenWH(getContext()).widthPixels;
        int screenHeight = CameraUtils.getScreenWH(getContext()).heightPixels;

        int width = screenWidth / 3;
        int height = screenHeight / 3;

        for (int i = width, j = 0; i < screenWidth && j < 2; i += width, j++) {
            canvas.drawLine(i, 0, i, screenHeight, mLinePaint);
        }
        for (int j = height, i = 0; j < screenHeight && i < 2; j += height, i++) {
            canvas.drawLine(0, j, screenWidth, j, mLinePaint);
        }
    }
}
