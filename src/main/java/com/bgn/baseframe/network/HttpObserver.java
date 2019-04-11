package com.bgn.baseframe.network;

import com.bgn.baseframe.R;
import com.bgn.baseframe.base.BaseView;
import com.bgn.baseframe.network.bean.BaseResult;
import com.bgn.baseframe.utils.NetworkUtil;
import com.bgn.baseframe.utils.UiUtil;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者：wl on 2017/9/28 11:00
 * 邮箱：wanglun@stosz.com
 */
public abstract class HttpObserver<T extends BaseResult> implements Observer<T> {
    private Disposable disposable;
    private BaseView baseView;

    public HttpObserver(BaseView view) {
        baseView = view;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
        baseView.addDisposable(d);
    }

    @Override
    public void onNext(T value) {
        baseView.hideLoadingView();
        baseView.hideRefreshView();
        if (value.isSucceed()) {
            onSucceed(value);
        } else {
            onDefeat(value);
        }
//        if (value.getCode() == HttpCode.TOKEN_ERROR || value.getCode() == HttpCode.TOKEN_OUT_DATE) {
//            ARouter.getInstance().build("/mall/loginOutActivity").withInt("type", value.getCode()).navigation();
//        }
        baseView.removeDisposable(disposable);
    }

    /**
     * 网络请求异常的回调
     */
    @Override
    public void onError(Throwable e) {
        baseView.removeDisposable(disposable);
        baseView.hideLoadingView();
        baseView.hideRefreshView();
        if (NetworkUtil.isNetworkerConnect()) {
            Logger.e("TEST：" + e.getMessage());
            baseView.showHint(UiUtil.getString(R.string.unknow_error));
        } else {
            baseView.showHint(UiUtil.getString(R.string.network_error));
        }

    }

    @Override
    public void onComplete() {
        Logger.d("完成" + "");
        baseView.hideLoadingView();
    }

    /**
     * 网络请求成功，业务处理成功的回调
     */
    public abstract void onSucceed(T value);

    /**
     * 网络请求成功，业务处理失败的回调
     */
    public void onDefeat(T value) {
        baseView.showHint(value.getError_msg());
    }


}
