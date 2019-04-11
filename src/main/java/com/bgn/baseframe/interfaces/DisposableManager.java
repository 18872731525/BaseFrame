package com.bgn.baseframe.interfaces;

import io.reactivex.disposables.Disposable;

/**
 * 作者：wl on 2017/9/26 10:32
 * 邮箱：wanglun@stosz.com
 */

//类说明：Rxjava的Disposable用来取消事件，这里的作用是放入activity中，页面关闭时取消网络请求
public interface DisposableManager {
    void addDisposable(Disposable observer);
    void removeDisposable(Disposable observer);
    void dispose();
}
