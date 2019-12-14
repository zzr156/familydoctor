package com.ylz.bizDo.jtapp.commonVo;

/**
 * Created by zzl on 2017/11/23.
 */
public class AppCommLyQvo {
    private String drId;//医生id
    private String perType;//履约类型
    private String followType;//随访类型
    private String patientIdno;//身份证
    private String fwType;//服务类型
    private String hospId;//医院id
    private String areaCode;//区域编号
    private String lyNum;//履约编号

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getPerType() {
        return perType;
    }

    public void setPerType(String perType) {
        this.perType = perType;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getFwType() {
        return fwType;
    }

    public void setFwType(String fwType) {
        this.fwType = fwType;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getLyNum() {
        return lyNum;
    }

    public void setLyNum(String lyNum) {
        this.lyNum = lyNum;
    }
}
