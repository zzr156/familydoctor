package com.ylz.bizDo.jtapp.commonVo;

/**
 * Created by zzl on 2019/2/15.
 */
public class AppInternetNewsSonQvo {
    private String patientIdNo;//身份证
    private String patientCard;//社保卡
    private String patientName;//姓名
    private String patientTel;//电话
    private String patientNeighborhoodCommittee;//行政区划居委会
    private String ehcId;//电子健康卡主键
    private String ehcCardNo;//电子健康卡卡号
    private String deviceType;//切换血压血糖界面 1为高血压,2为糖尿病  这个类型在JKJC健康监测需要传 默认为高血压
    private String type;//调用类型

    public String getPatientIdNo() {
        return patientIdNo;
    }

    public void setPatientIdNo(String patientIdNo) {
        this.patientIdNo = patientIdNo;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getPatientNeighborhoodCommittee() {
        return patientNeighborhoodCommittee;
    }

    public void setPatientNeighborhoodCommittee(String patientNeighborhoodCommittee) {
        this.patientNeighborhoodCommittee = patientNeighborhoodCommittee;
    }

    public String getEhcId() {
        return ehcId;
    }

    public void setEhcId(String ehcId) {
        this.ehcId = ehcId;
    }

    public String getEhcCardNo() {
        return ehcCardNo;
    }

    public void setEhcCardNo(String ehcCardNo) {
        this.ehcCardNo = ehcCardNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
