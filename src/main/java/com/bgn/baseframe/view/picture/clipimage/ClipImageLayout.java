package com.bgn.baseframe.view.picture.clipimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;


public class ClipImageLayout extends RelativeLayout {

    public ClipZoomImageView mZoomImageView;
    private ClipImageBorderView mClipImageView;

    /**
     * 这里测试，直接写死了大小，真正使用过程中，可以提取为自定义属性
     */
    private int mHorizontalPadding = 10;

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mZoomImageView = new ClipZoomImageView(context);
        mClipImageView = new ClipImageBorderView(context);

        android.view.ViewGroup.LayoutParams lp = new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);

        this.addView(mZoomImageView, lp);
        this.addView(mClipImageView, lp);


        // 计算padding的px
        mHorizontalPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources()
                        .getDisplayMetrics());
        mClipImageView.setHorizontalPadding(mHorizontalPadding);
        mZoomImageView.setHorizontalPadding(mHorizontalPadding);
    }

    public void setIsGriddingFrame (boolean isGriddFrame) {
        mClipImageView.setIsGriddingFrame(isGriddFrame);
    }

    public void setBorderColore (int  colorId) {
        mClipImageView.setBorderColor(colorId);
    }
    public void setBorderWidth(int borderWidth) {
        mClipImageView.setBorderWidth(borderWidth);
    }


    public void setImageBitmap(Bitmap bitmap) {
        mZoomImageView.setImageBitmap(bitmap);
    }

    /**
     * 对外公布设置边距的方法,单位为dp
     *
     * @param mHorizontalPadding
     */
    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    /**
     * 裁切图片
     *
     * @return
     */
    public Bitmap clip() {
        return mZoomImageView.clip();
    }

    /**
     * 设置是否图片全部显示
     *
     * @param allShowPicture
     */
    public void setAllShowPicture(boolean allShowPicture) {
        mZoomImageView.setAllShowPicture(allShowPicture);
    }

}
