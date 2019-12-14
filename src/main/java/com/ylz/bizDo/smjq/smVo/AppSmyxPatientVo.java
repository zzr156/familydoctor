package com.ylz.bizDo.smjq.smVo;

/**
 * Created by zzl on 2018/8/14.
 */
public class AppSmyxPatientVo {
    private String areaCode;//区域编码
    private String patientId;//居民主键
    private String xAxis;//x轴
    private String yAxis;//y轴
    private String hospId;//机构id

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getxAxis() {
        return xAxis;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public String getyAxis() {
        return yAxis;
    }

    public void setyAxis(String yAxis) {
        this.yAxis = yAxis;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }
}
