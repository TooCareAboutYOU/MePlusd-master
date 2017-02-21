package com.ivt.android.me.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class T1 implements Serializable {

    private String errorCode;
    private String errorMessage;
    private T2 data;

    public T2 getData() {
        return data;
    }

    public void setData(T2 data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "T1{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
