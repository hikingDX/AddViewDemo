package com.hiking.addviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/21.
 */
public class FatherView extends LinearLayout {
    Context mContext;
    private Paint mPaint = new Paint();
    private int mLen;
    private ViewPager mViewPager;
    private float mTranslationX;
    private double prePosition;

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
        textView.setBackgroundColor(Color.RED);
        textView.setText(s);
        textView.setTextSize(20);
        mPaint.setTextSize(textView.getTextSize());
        mLen = (int) mPaint.measureText(s);
        textView.setPadding(10, 0, 10, 0);
        addView(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mPaint.setColor(Color.BLUE);
        RectF rect = new RectF(10 + mTranslationX, getHeight() - 5, mTranslationX + 10 + mLen, getHeight());
        canvas.drawRect(rect, mPaint);
    }

    /**
     * 指示器跟随手指滚动，以及容器滚动
     *
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset) {
        // 不断改变偏移量，invalidate
        //1.获取移动后的标题x值
        if (getChildCount() > 0) {
//            if (position!= prePosition) {
                mTranslationX = getChildAt(position).getX();
                TextView textView = (TextView) getChildAt(position);
                mPaint.setTextSize(textView.getTextSize());
                mLen = (int) mPaint.measureText(textView.getText().toString());
                invalidate();
                prePosition = position;
//            }
        }
    }

    // 设置关联的ViewPager
    public void setViewPager(ViewPager mViewPager, int pos) {
        this.mViewPager = mViewPager;

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 设置字体颜色高亮
                //                resetTextViewColor();
                //                highLightTextView(position);

                //                // 回调
                //                if (onPageChangeListener != null)
                //                {
                //                    onPageChangeListener.onPageSelected(position);
                //                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                // 滚动

                scroll(position, positionOffset);

                // 回调
                //                if (onPageChangeListener != null)
                //                {
                //                    onPageChangeListener.onPageScrolled(position,
                //                            positionOffset, positionOffsetPixels);
                //                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 回调
                //                if (onPageChangeListener != null) {
                //                    onPageChangeListener.onPageScrollStateChanged(state);
                //                }

            }
        });
        // 设置当前页
        mViewPager.setCurrentItem(pos);
        // 高亮
        //        highLightTextView(pos);
    }
}
