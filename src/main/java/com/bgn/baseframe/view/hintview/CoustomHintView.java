package com.bgn.baseframe.view.hintview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.jude.rollviewpager.Util;
import com.jude.rollviewpager.hintview.ShapeHintView;

/**
 * Created by wanglun on 2018/3/28.
 */

public class CoustomHintView extends ShapeHintView {
    private int focusColor;
    private int normalColor;
    private int hintSzie;

    public CoustomHintView(Context context, int focusColor, int normalColor, int size) {
        super(context);
        this.focusColor = focusColor;
        this.normalColor = normalColor;
        this.hintSzie = size;
    }

    @Override
    public Drawable makeFocusDrawable() {
        GradientDrawable dot_focus = new GradientDrawable();
        dot_focus.setColor(focusColor);
        dot_focus.setCornerRadius(Util.dip2px(getContext(), 90));
        dot_focus.setSize(Util.dip2px(getContext(), hintSzie), Util.dip2px(getContext(), hintSzie));
        return dot_focus;
    }

    @Override
    public Drawable makeNormalDrawable() {
        GradientDrawable dot_normal = new GradientDrawable();
        dot_normal.setColor(normalColor);
        dot_normal.setCornerRadius(Util.dip2px(getContext(), 90));
        dot_normal.setSize(Util.dip2px(getContext(), hintSzie), Util.dip2px(getContext(), hintSzie));
        return dot_normal;
    }
}
