package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2018/7/18.
 */
public class AppManageArchivingCountQvo extends CommConditionVo {
    private String patientCity;//市
    private String patientArea;//县
    private String patientStreet;//乡镇
    private String hospId;//机构id
    private String startDate;//开始时间
    private String endDate;//结束时间
    private String areaCode;//区域编码
    private String role;
    private String isFindState;//是否查询居委会机构
    private String jdSourceType;

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

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIsFindState() {
        return isFindState;
    }

    public void setIsFindState(String isFindState) {
        this.isFindState = isFindState;
    }

    public String getJdSourceType() {
        return jdSourceType;
    }

    public void setJdSourceType(String jdSourceType) {
        this.jdSourceType = jdSourceType;
    }
}
