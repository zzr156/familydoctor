package com.ylz.bizDo.app.entity;

/**
 * Created by lenovo on 2018/1/25.
 */
public class FamilyMemberVo {

    private String patientjmda; //居民档案号
    private String patientidno; // 居民身份证
    private String urlType;
    private String patientname; //名称
    private String patientrelationship; //与户主关系
    private String hospAreaCode;
    private String patientstate;//签约状态

    public String getPatientjmda() {
        return patientjmda;
    }

    public void setPatientjmda(String patientjmda) {
        this.patientjmda = patientjmda;
    }

    public String getPatientidno() {
        return patientidno;
    }

    public void setPatientidno(String patientidno) {
        this.patientidno = patientidno;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getPatientrelationship() {
        return patientrelationship;
    }

    public void setPatientrelationship(String patientrelationship) {
        this.patientrelationship = patientrelationship;
    }

    public String getHospAreaCode() {
        return hospAreaCode;
    }

    public void setHospAreaCode(String hospAreaCode) {
        this.hospAreaCode = hospAreaCode;
    }

    public String getPatientstate() {
        return patientstate;
    }

    public void setPatientstate(String patientstate) {
        this.patientstate = patientstate;
    }
}
