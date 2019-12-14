package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * 互联网医院专用请求类
 * Created by zzl on 2019/3/18.
 */
public class InternetHospQvo extends CommConditionVo {
    private String patientIdno;//居民身份证
    private String orgId;//机构id
    private String pkId;//服务包主键
    private String areaCode;//行政区划

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
