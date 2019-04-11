package com.bgn.baseframe.utils.evenbus;


/**
 * Created by wanglun on 2018/3/2.
 */

public class MessageEvent {
    private String evenCode;
    private String dataString;
    private Object data;


    public String getEvenCode() {
        return evenCode;
    }

    public void setEvenCode(String evenCode) {
        this.evenCode = evenCode;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }



    public MessageEvent(String code, String datatest, Object obj) {
        evenCode = code;
        dataString = datatest;
        data = obj;
    }

}
