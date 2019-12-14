package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/6/16.
 */
public class AppTeamQvo extends CommConditionVo {
    private String appTeamId;//团队名称
    private String appTeamDrId;//家庭医生
    private String appTeamState;//有效标志
    private String appTeamHospName;//机构名称
    private String drHospId;//所属医院
    private String pro;//省
    private String city;//市
    private String area;//区
    private String appTeamDrName;//家庭医生姓名

    public String getAppTeamId() {
        return appTeamId;
    }

    public void setAppTeamId(String appTeamId) {
        this.appTeamId = appTeamId;
    }

    public String getAppTeamDrId() {
        return appTeamDrId;
    }

    public void setAppTeamDrId(String appTeamDrId) {
        this.appTeamDrId = appTeamDrId;
    }

    public String getAppTeamState() {
        return appTeamState;
    }

    public void setAppTeamState(String appTeamState) {
        this.appTeamState = appTeamState;
    }

    public String getAppTeamHospName() {
        return appTeamHospName;
    }

    public void setAppTeamHospName(String appTeamHospName) {
        this.appTeamHospName = appTeamHospName;
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

    public String getAppTeamDrName() {
        return appTeamDrName;
    }

    public void setAppTeamDrName(String appTeamDrName) {
        this.appTeamDrName = appTeamDrName;
    }
}
