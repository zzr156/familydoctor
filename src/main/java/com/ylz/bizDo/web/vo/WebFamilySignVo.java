package com.ylz.bizDo.web.vo;

import java.util.Calendar;
import java.util.List;

/**
 * Created by lenovo on 2018/1/2.
 */
public class WebFamilySignVo {
    // 地区编码
    private String patientProvince;//省
    private String patientCity;//市
    private String patientArea;//区
    private String patientStreet;//街道
    private String patientNeighborhoodCommittee;//居委会
    private String patientAddress;//患者地址
    //// 甲方信息
    private String hospId;//医院主键
    private String hospName;//医院名称

    private String teamId;//团队id
    private String teamName;//团队名称

    private String drId;//医生主键
    private String drName;//医生名称
    private String drTel;//医生电话

    private String signDrAssistantId;
    private String signDrAssistantTel;
    private String signDrAssistantName;

    //
    private String signDate;//签约时间
    private Calendar signFromDate;//有效开始时间
    private Calendar signToDate;//有效结束时间

    // 家庭成员 实体
    private List<WebFamilySubpageSignVo> familyVo;

    private String signWebState;
    private String batchOperatorId;
    private String batchOperatorName;
    private String[] sJjType;


    public String getPatientProvince() {
        return patientProvince;
    }

    public void setPatientProvince(String patientProvince) {
        this.patientProvince = patientProvince;
    }

    public String getPatientCity() {
        return patientCity;
    }

    public void setPatientCity(String patientCity) {
        this.patientCity = patientCity;
    }

    public String getPatientArea() {
        return patientArea;
    }

    public void setPatientArea(String patientArea) {
        this.patientArea = patientArea;
    }

    public String getPatientStreet() {
        return patientStreet;
    }

    public void setPatientStreet(String patientStreet) {
        this.patientStreet = patientStreet;
    }

    public String getPatientNeighborhoodCommittee() {
        return patientNeighborhoodCommittee;
    }

    public void setPatientNeighborhoodCommittee(String patientNeighborhoodCommittee) {
        this.patientNeighborhoodCommittee = patientNeighborhoodCommittee;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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

    public String getSignDrAssistantId() {
        return signDrAssistantId;
    }

    public void setSignDrAssistantId(String signDrAssistantId) {
        this.signDrAssistantId = signDrAssistantId;
    }

    public String getSignDrAssistantTel() {
        return signDrAssistantTel;
    }

    public void setSignDrAssistantTel(String signDrAssistantTel) {
        this.signDrAssistantTel = signDrAssistantTel;
    }

    public String getSignDrAssistantName() {
        return signDrAssistantName;
    }

    public void setSignDrAssistantName(String signDrAssistantName) {
        this.signDrAssistantName = signDrAssistantName;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public Calendar getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(Calendar signFromDate) {
        this.signFromDate = signFromDate;
    }

    public Calendar getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(Calendar signToDate) {
        this.signToDate = signToDate;
    }

    public List<WebFamilySubpageSignVo> getFamilyVo() {
        return familyVo;
    }

    public void setFamilyVo(List<WebFamilySubpageSignVo> familyVo) {
        this.familyVo = familyVo;
    }

    public String getSignWebState() {
        return signWebState;
    }

    public void setSignWebState(String signWebState) {
        this.signWebState = signWebState;
    }

    public String getBatchOperatorId() {
        return batchOperatorId;
    }

    public void setBatchOperatorId(String batchOperatorId) {
        this.batchOperatorId = batchOperatorId;
    }

    public String getBatchOperatorName() {
        return batchOperatorName;
    }

    public void setBatchOperatorName(String batchOperatorName) {
        this.batchOperatorName = batchOperatorName;
    }

    public String[] getsJjType() {
        return sJjType;
    }

    public void setsJjType(String[] sJjType) {
        this.sJjType = sJjType;
    }
}
