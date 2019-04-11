package com.bgn.baseframe.utils.evenbus;

/**
 * Created by wanglun on 2018/3/22.
 */

public class MainEvent {
    public static final String UPDATE_CART = "update_ucart";
    public static final String SHARE_SUCCEED = "share_succeed";
    public static final String SHARE_FAILD = "share_faild";
    private String eventName;
    private Object data;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public MainEvent(String eventName, Object data) {
        this.eventName = eventName;
        this.data = data;
    }

    public MainEvent(String eventName) {
        this.eventName = eventName;

    }
}
