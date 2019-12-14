package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/8/16.
 */
public class AppServeEconQvo extends CommConditionVo {
    private String content;
    private String roleType;
    private String areaCode;
    private String hospId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }
}
