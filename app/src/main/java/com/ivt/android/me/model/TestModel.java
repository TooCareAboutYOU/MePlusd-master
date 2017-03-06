package com.ivt.android.me.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class TestModel implements Parcelable {

    private String name;
    private int age;
    private boolean sex;

    public TestModel(String name, int age, boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    protected TestModel(Parcel in) {
        name = in.readString();
        age = in.readInt();
        sex = in.readInt() != 1;
    }

    public static final Creator<TestModel> CREATOR = new Creator<TestModel>() {
        @Override
        public TestModel createFromParcel(Parcel in) {  return new TestModel(in); }

        @Override
        public TestModel[] newArray(int size) { return new TestModel[size]; }
    };

    @Override
    public int describeContents() {  return 0;  }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(age);
        parcel.writeInt(sex ? 1 : 0);
    }

}
