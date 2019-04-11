package com.bgn.baseframe.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.ParameterizedType;

/**
 * wanglun@stosz.com
 */

public abstract class MVPBaseListFragment< V extends BaseView, T extends BasePresenterImpl<V>> extends BaseFragment {
    public T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    public abstract <T> T getInstance(Object o, int i) ;
}
