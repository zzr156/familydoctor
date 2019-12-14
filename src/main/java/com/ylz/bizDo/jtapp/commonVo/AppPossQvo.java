package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * poss机请求参数
 * Created by zzl on 2018/8/20.
 */
public class AppPossQvo extends CommConditionVo {
    private String possSn;//poss机sn码
    private String glucometerSn;//血糖仪sn码
    private String sphygmomanometerSn;//血压计sn码
    private String drId;//医生id
    private String imageUrl;//图片地址
    private String patientId;//居民主键
    private String year;//年份
    private String id;//主键
    private String signState;//签约状态
    private String signId;//签约单主键
    private String patientIdno;//居民身份证


    public String getPossSn() {
        return possSn;
    }

    public void setPossSn(String possSn) {
        this.possSn = possSn;
    }

    public String getGlucometerSn() {
        return glucometerSn;
    }

    public void setGlucometerSn(String glucometerSn) {
        this.glucometerSn = glucometerSn;
    }

    public String getSphygmomanometerSn() {
        return sphygmomanometerSn;
    }

    public void setSphygmomanometerSn(String sphygmomanometerSn) {
        this.sphygmomanometerSn = sphygmomanometerSn;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }
}
