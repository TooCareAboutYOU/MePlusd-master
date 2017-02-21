package com.ivt.android.me.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class T2 implements Serializable {

    private List<String> banner;
    private List<T3> listHot;

    @Override
    public String toString() {
        return "T2{" +
                "banner=" + banner +
                ", listHot=" + listHot +
                '}';
    }

    public List<String> getBanner() {
        return banner;
    }

    public void setBanner(List<String> banner) {
        this.banner = banner;
    }

    public List<T3> getListHot() {
        return listHot;
    }

    public void setListHot(List<T3> listHot) {
        this.listHot = listHot;
    }
}
