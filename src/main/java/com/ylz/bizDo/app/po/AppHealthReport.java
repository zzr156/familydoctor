package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by WangCheng on 2018/11/17.
 */
@Entity
@Table(name = "APP_HEALTH_REPORT")
public class AppHealthReport extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键

    @Column(name = "SIGN_LABLE_ID",length = 36)
    private String signLableId;//签约表主键

    @Column(name = "PATIENT_NAME",length = 20)
    private String patientName;//居民姓名

    @Column(name = "PATIENT_GENDER",length = 10)
    private String patientGender;//居民性别

    @Column(name = "PATIENT_AGE",length = 10)
    private String patientAge;//居民年龄

    @Column(name = "REPORT_DATE",length = 20)
    private String reportDate;//报告时间

    @Column(name = "SIGN_TO_DATE",length = 36)
    private String signToDate;//签约协议日期

    @Column(name = "HOSP_AREA_CODE",length = 36)
    private String hospAreaCode;//行政编号

    @Column(name = "ORG_ID",length = 40)
    private String orgId;//机构ID

    @Column(name = "PATIENT_ID_NO",length = 20)
    private String patientIdNo;//身份证

    @Column(name = "MEDICAL_HISTORY",length = 100)
    private String medicalHistory;//既往病史

    @Column(name = "SIGN_PERS_GROUP",length = 40)
    private String signPersGroup;//服务人群类型

    @Column(name = "RESULT_JSON")
    private String resultJson;//调用基卫返回的JSON串

    @Column(name = "SUMMARY",length = 255)
    private String summary;//总结

    @Column(name = "HEALTH_ASSESSMENT",length = 255)
    private String healthAssessment;//健康评价

    @Column(name = "HEALTH_GUIDANCE",length = 255)
    private String healthGuidance;//健康指导

    @Column(name = "DOCTOR_ID",length = 40)
    private String doctorId;//报告医生ID

    @Column(name = "REPORT_STATE",length = 2)
    private String reportState =  "1";//报告状态(1:有效，0：无效)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
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

    public String getReportState() {
        return reportState;
    }

    public void setReportState(String reportState) {
        this.reportState = reportState;
    }
}
