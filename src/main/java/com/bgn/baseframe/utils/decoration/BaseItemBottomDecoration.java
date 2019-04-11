package com.bgn.baseframe.utils.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bgn.baseframe.utils.UiUtil;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class BaseItemBottomDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public BaseItemBottomDecoration(int space) {
        this.space = UiUtil.dip2px(space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

//        if (parent.getChildPosition(view) != 0){
//            outRect.top = space;
//        }else{
//            outRect.top = 0;
//        }
        outRect.bottom = space;
    }
}
