package com.ylz.bizDo.performance.vo;

/**
 * Created by hzk on 2017/6/29.
 * 健康分布
 */
public class HealthGroupVo {
    private String patientId;//患者id
    private String patientName;//名字
    private String patientAddress;//地址
    private String patientTel;//电话
    private String patientOrdinate;//纵坐标
    private String patientAbscissa;//横坐标
    private String patientAge;//年龄
    private String labelTitle;//疾病类型
    private String labelColor;//疾病颜色

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public void setPatientOrdinate(String patientOrdinate) {
        this.patientOrdinate = patientOrdinate;
    }

    public void setPatientAbscissa(String patientAbscissa) {
        this.patientAbscissa = patientAbscissa;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public String getPatientOrdinate() {
        return patientOrdinate;
    }

    public String getPatientAbscissa() {
        return patientAbscissa;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public String getLabelColor() {
        return labelColor;
    }
}

