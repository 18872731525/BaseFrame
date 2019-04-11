package com.bgn.baseframe.network.bean;

/**
 * 作者：wl on 2017/9/21 15:36
 * 邮箱：wanglun@stosz.com
 */
public class BaseResult implements IBaseResult {

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean isSucceed() {
        return 1 == status.getSucceed();
    }

    @Override
    public String getError_msg() {
        return status.getError_desc();
    }

    @Override
    public String getError_code() {
        return status.getError_code();
    }
}
