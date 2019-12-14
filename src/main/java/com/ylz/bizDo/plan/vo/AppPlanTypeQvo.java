package com.ylz.bizDo.plan.vo;

/**
 * Created by asus on 2017/6/21.
 */
public class AppPlanTypeQvo {
    private String patientId;//随访主键
    private String type;//随访类型
    private String delayDate;//延期日期


    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDelayDate() {
        return delayDate;
    }

    public void setDelayDate(String delayDate) {
        this.delayDate = delayDate;
    }
}
