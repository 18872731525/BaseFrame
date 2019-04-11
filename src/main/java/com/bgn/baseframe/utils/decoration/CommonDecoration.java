package com.bgn.baseframe.utils.decoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.bgn.baseframe.utils.UiUtil;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class CommonDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int leftRight;
    private int topBottom;
    private boolean isLinearLayoutManager=false;

    public CommonDecoration(int space) {
        // this.space = UiUtil.dip2px(space);
        this.leftRight = UiUtil.dip2px(space);
        this.topBottom = UiUtil.dip2px(space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int layoutManagerSpanCount = 0;
        int totalCount = 0;
        int orientation = 0;

        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            layoutManagerSpanCount = ((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount();
            totalCount = ((StaggeredGridLayoutManager) parent.getLayoutManager()).getItemCount();
            orientation = ((StaggeredGridLayoutManager) parent.getLayoutManager()).getOrientation();
        } else if (parent.getLayoutManager() instanceof GridLayoutManager) {
            layoutManagerSpanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
            totalCount = ((GridLayoutManager) parent.getLayoutManager()).getItemCount();
            orientation = ((GridLayoutManager) parent.getLayoutManager()).getOrientation();
        } else {
            isLinearLayoutManager=true;
            layoutManagerSpanCount = 1;
            totalCount = ((LinearLayoutManager) parent.getLayoutManager()).getItemCount();
            orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        }

        //判断总的数量是否可以整除
        int surplusCount = totalCount % layoutManagerSpanCount;
        int childPosition = parent.getChildAdapterPosition(view);
        if (orientation == GridLayoutManager.VERTICAL) {//竖直方向的
            if (surplusCount == 0 && childPosition > totalCount - layoutManagerSpanCount - 1) {
                //后面几项需要bottom
                outRect.bottom = topBottom;
            } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                outRect.bottom = topBottom;
            }
            if ((childPosition + 1) % layoutManagerSpanCount == 0) {//被整除的需要右边
                if(!isLinearLayoutManager)
                    outRect.right = leftRight;
            }
            outRect.top = topBottom;
            if(!isLinearLayoutManager)
                outRect.left = leftRight;
        } else {
            if (surplusCount == 0 && childPosition > totalCount - layoutManagerSpanCount - 1) {
                //后面几项需要右边
                outRect.right = leftRight;
            } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                outRect.right = leftRight;
            }
            if ((childPosition + 1) % layoutManagerSpanCount == 0) {//被整除的需要下边
                if(!isLinearLayoutManager)
                    outRect.bottom = topBottom;
            }
            if(!isLinearLayoutManager)
                outRect.top = topBottom;
            outRect.left = leftRight;
        }
    }

}
