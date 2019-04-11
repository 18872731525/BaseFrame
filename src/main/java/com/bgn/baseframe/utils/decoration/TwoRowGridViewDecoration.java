package com.bgn.baseframe.utils.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.bgn.baseframe.R;
import com.bgn.baseframe.utils.UiUtil;
import com.orhanobut.logger.Logger;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class TwoRowGridViewDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int leftRight;
    private int topBottom;

    public TwoRowGridViewDecoration(int space2) {

        space = (UiUtil.getScreenWidth() - UiUtil.getDimens(R.dimen.dp160) * 2) / 3;
        Logger.d("TTTtest" + space);
        this.leftRight = space;
        this.topBottom = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int layoutManagerSpanCount = 0;
        int totalCount = 0;

        layoutManagerSpanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        totalCount = ((GridLayoutManager) parent.getLayoutManager()).getItemCount();

//
//        //判断总的数量是否可以整除
//
        int surplusCount = totalCount % layoutManagerSpanCount;
        int childPosition = parent.getChildAdapterPosition(view);
        if (surplusCount == 0 && childPosition > totalCount - layoutManagerSpanCount - 1) {
            //后面几项需要bottom
            outRect.bottom = topBottom;
        } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
            outRect.bottom = topBottom;
        }
        if ((childPosition + 1) % layoutManagerSpanCount == 0) {//被整除的需要右边
            outRect.right = leftRight;
            outRect.left = leftRight / 2;
        } else {
            outRect.right = leftRight / 2;
            outRect.left = leftRight;
        }
        outRect.top = topBottom;


    }

}
