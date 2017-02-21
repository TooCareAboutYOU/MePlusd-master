package com.ivt.android.me.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class T3 implements Serializable {

    private String activityCategory;
    private String activityId;
    private String activityName;
    private int activityStatus;
    private String channelId;
    private String coverImgUrl;
    private int duration;
    private String endTime;
    private String extensions;
    private int isHorizontalScreen;
    private int showType;
    private String startTime;
    private String nickName;
    private String faceUrl;
    private String vipLevel;
    private String personTime;
    private String address;
    private String gender;
    private String meActionType;

    @Override
    public String toString() {
        return "T3{" +
                "activityCategory='" + activityCategory + '\'' +
                ", activityId='" + activityId + '\'' +
                ", activityName='" + activityName + '\'' +
                ", activityStatus=" + activityStatus +
                ", channelId='" + channelId + '\'' +
                ", coverImgUrl='" + coverImgUrl + '\'' +
                ", duration=" + duration +
                ", endTime='" + endTime + '\'' +
                ", extensions='" + extensions + '\'' +
                ", isHorizontalScreen=" + isHorizontalScreen +
                ", showType=" + showType +
                ", startTime='" + startTime + '\'' +
                ", nickName='" + nickName + '\'' +
                ", faceUrl='" + faceUrl + '\'' +
                ", vipLevel='" + vipLevel + '\'' +
                ", personTime='" + personTime + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", meActionType='" + meActionType + '\'' +
                '}';
    }

    public String getMeActionType() {
        return meActionType;
    }

    public void setMeActionType(String meActionType) {
        this.meActionType = meActionType;
    }

    public String getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(String activityCategory) {
        this.activityCategory = activityCategory;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(int activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public int getIsHorizontalScreen() {
        return isHorizontalScreen;
    }

    public void setIsHorizontalScreen(int isHorizontalScreen) {
        this.isHorizontalScreen = isHorizontalScreen;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getPersonTime() {
        return personTime;
    }

    public void setPersonTime(String personTime) {
        this.personTime = personTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
