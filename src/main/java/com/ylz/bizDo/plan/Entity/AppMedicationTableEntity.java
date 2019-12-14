package com.ylz.bizDo.plan.Entity;

/**用药情况和用药指导数据表
 * Created by admin on 2017-05-14.
 */
public class AppMedicationTableEntity implements java.io.Serializable{
    private String id;//
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
