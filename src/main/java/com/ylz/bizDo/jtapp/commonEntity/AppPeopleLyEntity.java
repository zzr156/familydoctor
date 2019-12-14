package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by zzl on 2017/12/26.
 */
public class AppPeopleLyEntity {
    private String patientId;//患者id
    private String patientName;//患者姓名
    private String patientImgUrl;//患者头像
    private String patientSex;//患者性别
    private String patientAge;//患者年龄
    private String labelColor;//疾病颜色
    private String labelTitle;//疾病名称

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientImgUrl() {
        return patientImgUrl;
    }

    public void setPatientImgUrl(String patientImgUrl) {
        this.patientImgUrl = patientImgUrl;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }
}
