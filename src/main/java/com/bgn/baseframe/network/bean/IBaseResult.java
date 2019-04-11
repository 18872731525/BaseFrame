package com.bgn.baseframe.network.bean;

/**
 * 作者：wl on 2017/9/21 15:29
 * 邮箱：wanglun@stosz.com
 */
public interface IBaseResult {
    boolean isSucceed();

    String getError_msg();

    String getError_code();

}
