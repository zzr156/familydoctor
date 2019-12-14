package com.ylz.bizDo.app.entity;

/**
 * 健康评估报告Vo
 * Created by WangCheng on 2018/11/07.
 */
public class WebHealthReportEntity {

    private String signLableId;//签约表主键
    private String patientName;//居民姓名
    private String patientGender;//居民性别
    private String patientAge;//居民年龄
    private String reportDate;//报告时间
    private String signToDate;//签约协议日期
    private String hospAreaCode;//行政编号
    private String orgId;//机构ID
    private String resultJson;//调用基卫返回的JSON串
    private String patientIdNo;//身份证
    private String medicalHistory;//既往病史
    private String signPersGroup;//服务人群类型
    private String summary;//总结
    private String healthAssessment;//健康评价
    private String healthGuidance;//健康指导
    private String doctorId;//报告医生ID

    public String getSignLableId() {
        return signLableId;
    }

    public void setSignLableId(String signLableId) {
        this.signLableId = signLableId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(String signToDate) {
        this.signToDate = signToDate;
    }

    public String getHospAreaCode() {
        return hospAreaCode;
    }

    public void setHospAreaCode(String hospAreaCode) {
        this.hospAreaCode = hospAreaCode;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public String getPatientIdNo() {
        return patientIdNo;
    }

    public void setPatientIdNo(String patientIdNo) {
        this.patientIdNo = patientIdNo;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getSignPersGroup() {
        return signPersGroup;
    }

    public void setSignPersGroup(String signPersGroup) {
        this.signPersGroup = signPersGroup;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getHealthAssessment() {
        return healthAssessment;
    }

    public void setHealthAssessment(String healthAssessment) {
        this.healthAssessment = healthAssessment;
    }

    public String getHealthGuidance() {
        return healthGuidance;
    }

    public void setHealthGuidance(String healthGuidance) {
        this.healthGuidance = healthGuidance;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
