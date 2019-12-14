package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by lenovo on 2018/1/25.
 */
public class FamilyMemberQvo extends CommConditionVo {

    private String patientjmda; //居民档案号
    private String patientidno; // 居民身份证
    private String urlType;
    private String hospAreaCode;

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

    public String getHospAreaCode() {
        return hospAreaCode;
    }

    public void setHospAreaCode(String hospAreaCode) {
        this.hospAreaCode = hospAreaCode;
    }
}
