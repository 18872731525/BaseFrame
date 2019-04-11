package com.bgn.baseframe.base;

/**
 *
 *  wanglun@stosz.com
 */

public class BasePresenterImpl<V> implements BasePresenter<V> {
    protected V mView;
    @Override
    public void attachView(V view) {
        mView=view;
    }

    @Override
    public void detachView() {
        mView=null;
    }
}
