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
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/21.
 */
public class FatherView extends LinearLayout {
    private Context mContext;
    private Paint mPaint = new Paint();
    private int mLen;
    private ViewPager mViewPager;
    private float mTranslationX;

    /**
     * 设置标题
     */
    public void setTitle(String title, int position) {
        for (int i = getChildCount() - 1; i > position; i--) {
            removeViewAt(i);
        }
        TextView textView = (TextView) getChildAt(position);
        textView.setText(title);
    }

    public FatherView(Context context) {
        this(context, null);
    }

    public FatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        mContext = context;
        addViewWithName("请选择");
    }

    public void addViewWithName(String s) {
        TextView textView = new TextView(mContext);
        textView.setTextColor(Color.BLACK);
        textView.setText(s);
        textView.setTextSize(20);
        textView.setPadding(10, 0, 10, 0);
        addView(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mPaint.setColor(Color.RED);
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
            mTranslationX = getChildAt(position).getX();
            TextView textView = (TextView) getChildAt(position);
            mPaint.setTextSize(textView.getTextSize());
            mLen = (int) mPaint.measureText(textView.getText().toString());
            invalidate();
        }
    }

    public void setPos(int pos) {
        mViewPager.setCurrentItem(pos);
        mViewPager.setBackgroundColor(Color.RED);
    }

    // 设置关联的ViewPager
    public void setViewPager(ViewPager viewPager, int pos) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                scroll(position, positionOffset);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // 设置当前页
        mViewPager.setCurrentItem(pos);
    }
}
