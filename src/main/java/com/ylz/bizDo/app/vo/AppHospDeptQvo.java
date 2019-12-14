package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/6/15.
 */
public class AppHospDeptQvo extends CommConditionVo {
    private String appHospName;
    private String drHospId;//所属医院
    private String pro;//省
    private String city;//市
    private String area;//区
    private String areaCode;//区域编号
    private String role;


    public String getAppHospName() {
        return appHospName;
    }

    public void setAppHospName(String appHospName) {
        this.appHospName = appHospName;
    }

    public String getDrHospId() {
        return drHospId;
    }

    public void setDrHospId(String drHospId) {
        this.drHospId = drHospId;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
