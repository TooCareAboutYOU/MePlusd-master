package com.cnlive.meplusd.model;

import java.io.Serializable;

/**
 * Created by zhangshuai on 2016-07-08.
 */
public class ErrorMessage implements Serializable {

    private String errorCode = "0";
    private String errorMessage = "";

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
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
}
