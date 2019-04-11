package com.bgn.baseframe.network.bean;

/**
 * 作者：wl on 2017/9/21 15:33
 * 邮箱：wanglun@stosz.com
 */
public class TDataBean<T> extends BaseResult {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
