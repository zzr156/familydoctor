package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/8/14.
 */
public class AppServeGroupQvo extends CommConditionVo {
    private String content;
    private String type;
    private String hospId;
    private String roleType;
    private String areaCode;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
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
}
