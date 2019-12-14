package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by zzl on 2019/3/20.
 */
public class InternetHospOrgEntity {
    private String id;//主键
    private String hospName;//医院名称
    private String hospCode;//医院编码
    private String hospAreaCode;//区域编码
    private String hospAddress;//医院地址
    private String hospTel;//医院电话
    private String hospIntro;//医院简介
    private String hospLevel;//等级 1省,2市,3区,4..社区

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getHospCode() {
        return hospCode;
    }

    public void setHospCode(String hospCode) {
        this.hospCode = hospCode;
    }

    public String getHospAreaCode() {
        return hospAreaCode;
    }

    public void setHospAreaCode(String hospAreaCode) {
        this.hospAreaCode = hospAreaCode;
    }

    public String getHospAddress() {
        return hospAddress;
    }

    public void setHospAddress(String hospAddress) {
        this.hospAddress = hospAddress;
    }

    public String getHospTel() {
        return hospTel;
    }

    public void setHospTel(String hospTel) {
        this.hospTel = hospTel;
    }

    public String getHospIntro() {
        return hospIntro;
    }

    public void setHospIntro(String hospIntro) {
        this.hospIntro = hospIntro;
    }

    public String getHospLevel() {
        return hospLevel;
    }

    public void setHospLevel(String hospLevel) {
        this.hospLevel = hospLevel;
    }
}
