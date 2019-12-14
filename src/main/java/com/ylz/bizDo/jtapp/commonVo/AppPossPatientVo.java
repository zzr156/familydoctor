package com.ylz.bizDo.jtapp.commonVo;

/**
 * 漳浦手持机保存服务记录传递参数
 * Created by zzl on 2018/10/30.
 */
public class AppPossPatientVo {
    private String id;//记录主键
    private String patientId;//居民主键
    private String patientName;//居民姓名
    private String signId;//签约单主键
    private String serveTime;//服务时间
    private String serveAddr;//服务地点(1家庭 2卫生所 3卫生院)
    private String hp;//高压
    private String lp;//低压
    private String bloodSugar;//血糖
    private String contentValue;//服务内容
    private String mainSituation;//主要情况告知
    private String healthGuidance;//健康指导
    private String patientAutograph;//接受服务对象签名
    private String drAutograph;//医生签名
    private String emergencyContactName;//紧急联系人
    private String emergencyContactTel;//紧急联系人电话
    private String drId;//医生主键

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getServeTime() {
        return serveTime;
    }

    public void setServeTime(String serveTime) {
        this.serveTime = serveTime;
    }

    public String getServeAddr() {
        return serveAddr;
    }

    public void setServeAddr(String serveAddr) {
        this.serveAddr = serveAddr;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getLp() {
        return lp;
    }

    public void setLp(String lp) {
        this.lp = lp;
    }

    public String getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(String bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public String getContentValue() {
        return contentValue;
    }

    public void setContentValue(String contentValue) {
        this.contentValue = contentValue;
    }

    public String getMainSituation() {
        return mainSituation;
    }

    public void setMainSituation(String mainSituation) {
        this.mainSituation = mainSituation;
    }

    public String getHealthGuidance() {
        return healthGuidance;
    }

    public void setHealthGuidance(String healthGuidance) {
        this.healthGuidance = healthGuidance;
    }

    public String getPatientAutograph() {
        return patientAutograph;
    }

    public void setPatientAutograph(String patientAutograph) {
        this.patientAutograph = patientAutograph;
    }

    public String getDrAutograph() {
        return drAutograph;
    }

    public void setDrAutograph(String drAutograph) {
        this.drAutograph = drAutograph;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactTel() {
        return emergencyContactTel;
    }

    public void setEmergencyContactTel(String emergencyContactTel) {
        this.emergencyContactTel = emergencyContactTel;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }
}
