package com.ivt.android.me.model;

import java.io.Serializable;

/**
 * Created by zhangshuai on 2017-01-06.
 */

public class User implements Serializable {

    private String name;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
