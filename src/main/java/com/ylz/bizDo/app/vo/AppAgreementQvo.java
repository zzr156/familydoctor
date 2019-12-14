package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by asus on 2017/6/15.
 */
public class AppAgreementQvo extends CommConditionVo {
    private String areaCity;
    private String hospId;
    private String orgName;//机构名称

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getAreaCity() {
        return areaCity;
    }

    public void setAreaCity(String areaCity) {
        this.areaCity = areaCity;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
