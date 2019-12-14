package com.ylz.bizDo.web.vo;

/**
 * Created by lenovo on 2018/1/2.
 */
public class WebFamilySubpageSignVo {



    private String patientId;//患者id
    private String patientName;//患者名字
    private String patientGender;//患者性别
    private String patientIdno;//患者身份证号
    private String patientCard;//患者社保卡
    private String patientTel;//患者电话
    private String patientjtda;//居民家庭档案
    private String patientjmda;//居民建康档案
    private String patientAge;//年纪
    private String mfFmNickName;//关系

    private String[] persGroup;
   // private String[] sJjType;
    private String signtext;//补充协议
    private String[] signpackageid;//服务包内容表id

    private String signzfpay; // 自费
    private String signczpay;//财政


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

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String[] getPersGroup() {
        return persGroup;
    }

    public void setPersGroup(String[] persGroup) {
        this.persGroup = persGroup;
    }



    public String getSigntext() {
        return signtext;
    }

    public void setSigntext(String signtext) {
        this.signtext = signtext;
    }

    public String[] getSignpackageid() {
        return signpackageid;
    }

    public void setSignpackageid(String[] signpackageid) {
        this.signpackageid = signpackageid;
    }

    public String getMfFmNickName() {
        return mfFmNickName;
    }

    public void setMfFmNickName(String mfFmNickName) {
        this.mfFmNickName = mfFmNickName;
    }

    public String getSignzfpay() {
        return signzfpay;
    }

    public void setSignzfpay(String signzfpay) {
        this.signzfpay = signzfpay;
    }

    public String getSignczpay() {
        return signczpay;
    }

    public void setSignczpay(String signczpay) {
        this.signczpay = signczpay;
    }
}
