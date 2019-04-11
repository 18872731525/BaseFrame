package com.bgn.baseframe.base;

/**
 *
 *  wanglun@stosz.com
 */

public interface  BasePresenter <V>{
    void attachView(V view);

    void detachView();
}
