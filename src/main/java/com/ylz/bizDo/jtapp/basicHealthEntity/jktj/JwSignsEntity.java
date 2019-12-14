package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import java.io.Serializable;

/**
 * 基卫体征数据
 * Created by zzl on 2017/11/9.
 */
public class JwSignsEntity implements Serializable {
    private String id;
    private String insertTime;
    private String modifyTime;
    private String dataSource;
    private String dataCity;
    private String dataStatus;
    private String examld;//体检唯一标识
    private String deviceNo;//设备号
    private String name;//姓名
    private String idno;//身份证
    private String birthday;//出生日期
    private String sex;//性别
    private String healthNo;//健康档案号
    private String examDate;//体检日期
    private BaseExam baseExam;//基本检查信息
    private BloodSugar bloodSugar;
    private String bloodLipids;
    private String bloodRoutine;
    private String urineRoutine;
    private String urineMicroalbumin;
    private String fecalOccultBlood;
    private String glycateHemog;
    private String hepatitisB;
    private String liverFunction;
    private String renalFuction;
    private String cardiogram;
    private String chestXray;
    private String chestXrayAbnormal;
    private String bultrasonic;
    private String bultrasonicAbnormal;
    private String cervicalSmear;
    private String cervicalSmearAbnormal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataCity() {
        return dataCity;
    }

    public void setDataCity(String dataCity) {
        this.dataCity = dataCity;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getExamld() {
        return examld;
    }

    public void setExamld(String examld) {
        this.examld = examld;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHealthNo() {
        return healthNo;
    }

    public void setHealthNo(String healthNo) {
        this.healthNo = healthNo;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public BaseExam getBaseExam() {
        return baseExam;
    }

    public void setBaseExam(BaseExam baseExam) {
        this.baseExam = baseExam;
    }

    public BloodSugar getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(BloodSugar bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public String getBloodLipids() {
        return bloodLipids;
    }

    public void setBloodLipids(String bloodLipids) {
        this.bloodLipids = bloodLipids;
    }

    public String getBloodRoutine() {
        return bloodRoutine;
    }

    public void setBloodRoutine(String bloodRoutine) {
        this.bloodRoutine = bloodRoutine;
    }

    public String getUrineRoutine() {
        return urineRoutine;
    }

    public void setUrineRoutine(String urineRoutine) {
        this.urineRoutine = urineRoutine;
    }

    public String getUrineMicroalbumin() {
        return urineMicroalbumin;
    }

    public void setUrineMicroalbumin(String urineMicroalbumin) {
        this.urineMicroalbumin = urineMicroalbumin;
    }

    public String getFecalOccultBlood() {
        return fecalOccultBlood;
    }

    public void setFecalOccultBlood(String fecalOccultBlood) {
        this.fecalOccultBlood = fecalOccultBlood;
    }

    public String getGlycateHemog() {
        return glycateHemog;
    }

    public void setGlycateHemog(String glycateHemog) {
        this.glycateHemog = glycateHemog;
    }

    public String getHepatitisB() {
        return hepatitisB;
    }

    public void setHepatitisB(String hepatitisB) {
        this.hepatitisB = hepatitisB;
    }

    public String getLiverFunction() {
        return liverFunction;
    }

    public void setLiverFunction(String liverFunction) {
        this.liverFunction = liverFunction;
    }

    public String getRenalFuction() {
        return renalFuction;
    }

    public void setRenalFuction(String renalFuction) {
        this.renalFuction = renalFuction;
    }

    public String getCardiogram() {
        return cardiogram;
    }

    public void setCardiogram(String cardiogram) {
        this.cardiogram = cardiogram;
    }

    public String getChestXray() {
        return chestXray;
    }

    public void setChestXray(String chestXray) {
        this.chestXray = chestXray;
    }

    public String getChestXrayAbnormal() {
        return chestXrayAbnormal;
    }

    public void setChestXrayAbnormal(String chestXrayAbnormal) {
        this.chestXrayAbnormal = chestXrayAbnormal;
    }

    public String getBultrasonic() {
        return bultrasonic;
    }

    public void setBultrasonic(String bultrasonic) {
        this.bultrasonic = bultrasonic;
    }

    public String getBultrasonicAbnormal() {
        return bultrasonicAbnormal;
    }

    public void setBultrasonicAbnormal(String bultrasonicAbnormal) {
        this.bultrasonicAbnormal = bultrasonicAbnormal;
    }

    public String getCervicalSmear() {
        return cervicalSmear;
    }

    public void setCervicalSmear(String cervicalSmear) {
        this.cervicalSmear = cervicalSmear;
    }

    public String getCervicalSmearAbnormal() {
        return cervicalSmearAbnormal;
    }

    public void setCervicalSmearAbnormal(String cervicalSmearAbnormal) {
        this.cervicalSmearAbnormal = cervicalSmearAbnormal;
    }
}
