package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/8/21.
 */
public class AppEconAndGovQvo extends CommConditionVo {
    private String econTitle;
    private String govTitle;
    private String RoleType;
    private String hospId;
    private String areaCode;

    public String getEconTitle() {
        return econTitle;
    }

    public void setEconTitle(String econTitle) {
        this.econTitle = econTitle;
    }

    public String getGovTitle() {
        return govTitle;
    }

    public void setGovTitle(String govTitle) {
        this.govTitle = govTitle;
    }

    public String getRoleType() {
        return RoleType;
    }

    public void setRoleType(String roleType) {
        RoleType = roleType;
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

}
