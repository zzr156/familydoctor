package com.ylz.bizDo.smjq.smEntity;

/**
 * Created by zzl on 2018/8/2.
 */
public class AppBloodSugarTwoEntity {
    private Double bloodSugarValue;//血糖值
    private String measureTime;//测量时间
    private String sourceType;//来源(1app 2智能设备 3随访 4门诊 5poss)
    private String ycState;//异常状态(0无异常 1异常)
    private String drId;
    private String drName;
    private String drTel;
    private String bloodRemark;//备注

    public Double getBloodSugarValue() {
        return bloodSugarValue;
    }

    public void setBloodSugarValue(Double bloodSugarValue) {
        this.bloodSugarValue = bloodSugarValue;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getYcState() {
        return ycState;
    }

    public void setYcState(String ycState) {
        this.ycState = ycState;
    }

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

    public String getDrTel() {
        return drTel;
    }

    public void setDrTel(String drTel) {
        this.drTel = drTel;
    }

    public String getBloodRemark() {
        return bloodRemark;
    }

    public void setBloodRemark(String bloodRemark) {
        this.bloodRemark = bloodRemark;
    }
}
