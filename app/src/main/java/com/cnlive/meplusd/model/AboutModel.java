package com.cnlive.meplusd.model;

import java.io.Serializable;

/**
 * Created by zhangshuai on 2017-01-16.
 */

public class AboutModel implements Serializable {

    private String id;
    private String msg;
    private String code;
    private String mark;
    private String extral;

    public AboutModel(String extral, String id, String msg, String code, String mark) {
        this.extral = extral;
        this.id = id;
        this.msg = msg;
        this.code = code;
        this.mark = mark;
    }

    public String getExtral() {
        return extral;
    }

    public void setExtral(String extral) {
        this.extral = extral;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "AboutModel{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", mark='" + mark + '\'' +
                ", extral='" + extral + '\'' +
                '}';
    }
}
