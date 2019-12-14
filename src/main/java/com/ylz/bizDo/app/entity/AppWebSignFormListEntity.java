package com.ylz.bizDo.app.entity;

import com.ylz.packcommon.common.util.ExtendDate;

/**
 *
 * web签约单
 */
public class AppWebSignFormListEntity {
    private String id;//签约单
    private String signhospid;
    private String hospname;
    private String signState;//签约状态 1:待签约 2:已签约 3:解约中 4:已解约 5:改签解约中 6:改签申请中 7:已退约
    private String patientName;//签约人员姓名
    private String patientGender;//性别
    private String patientIdno;//身份证号
    private String patientCard;//社保卡
    private String patientTel;//电话
    private String patientAge;//年龄
    private String patientId;//患者主键
    private String patientjtda;//居民健康档案
    private String patientjmda;//居民家庭档案
    private String patientProvince;//省
    private String patientCity;//市
    private String patientArea;//区
    private String patientStreet;//街道
    private String patientNeighborhoodCommittee;//居委会
    private String patientAddress;//地址



    private String patientImageurl;//头像
    private String teamHospName;//医院名称 签约机构
    private String teamName;//团队名称
    private String teamId;//团队id 查看协议单使用
    private String signFromDate;//有效开始时间
    private String signToDate;//有效结束时间
    private String signWay;//签约方式 0代表自己 1代表家人代签 2代表医生代签




    private String signDate;////签约时间

    private String drName;//医生名称
    private String fwImageName;//服务图片名称
    private String fwb;//服务包

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignhospid() {
        return signhospid;
    }

    public void setSignhospid(String signhospid) {
        this.signhospid = signhospid;
    }

    public String getHospname() {
        return hospname;
    }

    public void setHospname(String hospname) {
        this.hospname = hospname;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
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

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientjtda() {
        return patientjtda;
    }

    public void setPatientjtda(String patientjtda) {
        this.patientjtda = patientjtda;
    }

    public String getPatientjmda() {
        return patientjmda;
    }

    public void setPatientjmda(String patientjmda) {
        this.patientjmda = patientjmda;
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

    public String getTeamHospName() {
        return teamHospName;
    }

    public void setTeamHospName(String teamHospName) {
        this.teamHospName = teamHospName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(String signFromDate) {
        this.signFromDate = signFromDate;
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(String signToDate) {
        this.signToDate = signToDate;
    }

    public String getSignWay() {
        return signWay;
    }

    public void setSignWay(String signWay) {
        this.signWay = signWay;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(java.sql.Timestamp signDate) {
        this.signDate = signDate!=null? ExtendDate.getYMD(signDate):"";
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getFwImageName() {
        return fwImageName;
    }

    public void setFwImageName(String fwImageName) {
        this.fwImageName = fwImageName;
    }

    public String getFwb() {
        return fwb;
    }

    public void setFwb(String fwb) {
        this.fwb = fwb;
    }
}
