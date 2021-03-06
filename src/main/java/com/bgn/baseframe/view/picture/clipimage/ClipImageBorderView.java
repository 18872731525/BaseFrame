package com.bgn.baseframe.view.picture.clipimage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.bgn.baseframe.R;
import com.bgn.baseframe.utils.UiUtil;


public class ClipImageBorderView extends View {
    /**
     * 水平方向与View的边距
     */
    private int mHorizontalPadding;
    /**
     * 垂直方向与View的边距
     */
    private int mVerticalPadding;
    /**
     * 绘制的矩形的宽度
     */
    private int mWidth;
    /**
     * 边框的颜色，默认为白色
     */
    private int mBorderColor = Color.parseColor("#FFFFFF");
    /**
     * 边框的宽度 单位dp
     */
    private int mBorderWidth = 1;

    /**
     * 设置是否是网格边框
     */
    private boolean isGriddingFrame;

    private Paint mPaint;

    public ClipImageBorderView(Context context) {
        this(context, null);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mBorderWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mBorderWidth, getResources()
                        .getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 计算矩形区域的宽度
        mWidth = getWidth() - 2 * mHorizontalPadding;
        // 计算距离屏幕垂直边界 的边距
        mVerticalPadding = (getHeight() - mWidth) / 2;
        mPaint.setColor(Color.parseColor("#aa000000"));
        mPaint.setStyle(Style.FILL);
        // 绘制左边1
        canvas.drawRect(0, 0, mHorizontalPadding, getHeight(), mPaint);
        // 绘制右边2
        canvas.drawRect(getWidth() - mHorizontalPadding, 0, getWidth(),
                getHeight(), mPaint);
        // 绘制上边3
        canvas.drawRect(mHorizontalPadding, 0, getWidth() - mHorizontalPadding,
                mVerticalPadding, mPaint);
        // 绘制下边4
        canvas.drawRect(mHorizontalPadding, getHeight() - mVerticalPadding,
                getWidth() - mHorizontalPadding, getHeight(), mPaint);
        // 绘制外边框
        if (isGriddingFrame) {
            //网格边框,裁剪用
            Drawable mDrawable = UiUtil.getDrawable(R.mipmap.tailor_bg);
            mDrawable.setBounds(mHorizontalPadding, mHorizontalPadding, getWidth() - mHorizontalPadding, getHeight() - mHorizontalPadding);
            mDrawable.draw(canvas);
        } else {
            //白色线条边框,预览用
            mPaint.setColor(mBorderColor);
            mPaint.setStrokeWidth(mBorderWidth);
            mPaint.setStyle(Style.STROKE);
            canvas.drawRect(mHorizontalPadding, mVerticalPadding, getWidth()
                    - mHorizontalPadding, getHeight() - mVerticalPadding, mPaint);


        }
    }

    public void setBorderColor(int intColor) {
        mBorderColor = intColor;
    }

    public void setBorderWidth(int borderWidth) {
        mBorderWidth = borderWidth;
    }

    public void setIsGriddingFrame(boolean isGriddFrame) {
        isGriddingFrame = isGriddFrame;
    }

    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }
}
