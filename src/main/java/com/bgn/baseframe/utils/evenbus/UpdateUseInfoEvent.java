package com.bgn.baseframe.utils.evenbus;

/**
 * Created by wanglun on 2018/3/22.
 */

public class UpdateUseInfoEvent {
    /*网络请求用户数据*/
    public static final int EVENT_HTTP_LOAD = 1;
    /*用户登出，被挤号*/
    public static final int EVENT_LOG_OUT = 2;
    /*本地数据更新了一下*/
    public static final int EVENT_LOCAL_SET = 3;

    private int code;
    private Object extendObj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getExtendObj() {
        return extendObj;
    }

    public void setExtendObj(Object extendObj) {
        this.extendObj = extendObj;
    }

    public UpdateUseInfoEvent(int codes) {
        this.code = codes;
    }
}
