package com.ylz.bizDo.jtapp.patientEntity;

/**
 * 完善资料
 */
public class AppPatientChangeInfoEntity {
    private String id;
    private String patientName;//姓名
    private String patientGender;//性别
    private String patientAddress;//地址
    private String patientImageurl;//头像
    private String patientImageName;//头像文件名
    private String patientProvince;//省
    private String patientCity;//市
    private String patientArea;//区
    private String patientStreet;//街道
    private String patientNeighborhoodCommittee;//社区
    private String patientHealthy;//是否授权健康档案
    private String patientOrdinate; //纵坐标
    private String patientAbscissa;//横坐标
    private String patientHeight;//身高
    private String patientWeight;//体重
    private String patientTel;//手机号
    private String economicsType;//经济类型 多个用分号隔开 “1;2”


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientImageurl() {
        return patientImageurl;
    }

    public void setPatientImageurl(String patientImageurl) {
        this.patientImageurl = patientImageurl;
    }

    public String getPatientImageName() {
        return patientImageName;
    }

    public void setPatientImageName(String patientImageName) {
        this.patientImageName = patientImageName;
    }

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

    public String getPatientHealthy() {
        return patientHealthy;
    }

    public void setPatientHealthy(String patientHealthy) {
        this.patientHealthy = patientHealthy;
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

    public String getPatientOrdinate() {
        return patientOrdinate;
    }

    public void setPatientOrdinate(String patientOrdinate) {
        this.patientOrdinate = patientOrdinate;
    }

    public String getPatientAbscissa() {
        return patientAbscissa;
    }

    public void setPatientAbscissa(String patientAbscissa) {
        this.patientAbscissa = patientAbscissa;
    }

    public String getPatientHeight() {
        return patientHeight;
    }

    public void setPatientHeight(String patientHeight) {
        this.patientHeight = patientHeight;
    }

    public String getPatientWeight() {
        return patientWeight;
    }

    public void setPatientWeight(String patientWeight) {
        this.patientWeight = patientWeight;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getEconomicsType() {
        return economicsType;
    }

    public void setEconomicsType(String economicsType) {
        this.economicsType = economicsType;
    }
}
