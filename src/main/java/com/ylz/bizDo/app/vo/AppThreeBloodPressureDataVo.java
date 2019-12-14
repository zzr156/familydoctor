package com.ylz.bizDo.app.vo;

/**
 * 第三方血压数据请求参数
 * Created by zzl on 2018/5/30.
 */
public class AppThreeBloodPressureDataVo {
    private String medicalRecordId;//测量结果ID
    private String authorizationCode;//第三方授权码
    private String deviceSim;//设备序列号
    private String highPressure;//收缩压
    private String lowVoltage;//舒张压
    private String pulse;//心率
    private String uploadTime;//上传时间 格式20160706103000
    private String aOrB;//A/B 档位标识
    private String idCard;
    private String sourceType;//数据来源（1app 2智能设备 3随访 4门诊）
    private String name;//姓名
    private String idno;//身份证

    public String getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(String medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getDeviceSim() {
        return deviceSim;
    }

    public void setDeviceSim(String deviceSim) {
        this.deviceSim = deviceSim;
    }

    public String getHighPressure() {
        return highPressure;
    }

    public void setHighPressure(String highPressure) {
        this.highPressure = highPressure;
    }

    public String getLowVoltage() {
        return lowVoltage;
    }

    public void setLowVoltage(String lowVoltage) {
        this.lowVoltage = lowVoltage;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getaOrB() {
        return aOrB;
    }

    public void setaOrB(String aOrB) {
        this.aOrB = aOrB;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
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
}
