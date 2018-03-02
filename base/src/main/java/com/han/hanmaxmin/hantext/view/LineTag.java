package com.han.hanmaxmin.hantext.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.han.hanmaxmin.R;
import com.han.hanmaxmin.common.log.L;

/**
 * Created by ptxy on 2018/2/28.
 */

public class LineTag extends HorizontalScrollView {
    public LineTag(Context context) {
        super(context);
    }

    public LineTag(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineTag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        L.e("LineTag", "onLayout------------------------------" + changed);
        if (changed) {
            final LinearLayoutCompat linearLayout = (LinearLayoutCompat) getChildAt(0);
            //  getChildAt(postion) ViewGroup 里面的方法 用来获取指定位置的视图，由于下边的setTopic(List<Topic> list)方法中添加的控件是
            // 一个LinearLayoutCompat布局，所以这里获取的就是他了
            int count = linearLayout.getChildCount();//计算一个LinearLayoutCompat布局中子控件的个数
            TextView localTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.text_null, linearLayout, false);
            localTextView.setText("...");//定义一个文本内容为"..."的TextView,作为溢出内容填充
            int width = 0;//初始化linearLayout宽度
            for (int i = 0; i < count; i++) {//遍历所用TextView控件，并累加计算当前linearLayout宽度
                width += linearLayout.getChildAt(i).getWidth();
                if (width+localTextView.getWidth() > getWidth()) {//如果当前遍历的第i个TextView时候，linearLayout宽度大于父布局宽度
                    linearLayout.removeViews(i - 1, count - i + 1); // 将当前TextView以后的所有TextView移除
                    linearLayout.addView(localTextView);//添加文本内容为"..."的TextView到末尾
                    break;
                }
            }
        }
    }
}
