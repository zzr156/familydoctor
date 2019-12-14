package com.ylz.bizDo.jtapp.drEntity;

/**
 * 医生多账号数据
 * Created by zzl on 2018/4/10.
 */
public class AppDrMAccountEntity {
    private String drId;//医生主键
    private String drName;//医生姓名
    private String hospName;//医院名称
    private String state;//状态
    private String drAccount;//账号

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDrAccount() {
        return drAccount;
    }

    public void setDrAccount(String drAccount) {
        this.drAccount = drAccount;
    }
}
