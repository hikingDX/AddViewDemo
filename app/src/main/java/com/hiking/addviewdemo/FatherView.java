package com.hiking.addviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/21.
 */
public class FatherView extends LinearLayout {
    Context mContext;
    private Paint mPaint = new Paint();
    private int mLen;

    public FatherView(Context context) {
        super(context);
        mContext = context;
    }

    public FatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void addViewWithName(String s) {
        TextView textView = new TextView(mContext);
        //        textView.setBackgroundColor(Color.RED);
        textView.setText(s);
        textView.setTextSize(20);
        mPaint.setTextSize(textView.getTextSize());
        mLen = (int) mPaint.measureText(s);
        addView(textView, 100, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(0,0,mLen,0,mPaint);
    }
}
