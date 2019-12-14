package com.ylz.bizDo.plan.vo;

/**
 * Created by zzl on 2018/1/25.
 */
public class AppBasicDrugQvo {
    private String medicineName;//药物名称
    private String userToDay;//每日（月）次
    private String userToTime;//每次剂量

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getUserToDay() {
        return userToDay;
    }

    public void setUserToDay(String userToDay) {
        this.userToDay = userToDay;
    }

    public String getUserToTime() {
        return userToTime;
    }

    public void setUserToTime(String userToTime) {
        this.userToTime = userToTime;
    }
}
