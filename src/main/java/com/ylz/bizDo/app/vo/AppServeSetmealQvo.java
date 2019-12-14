package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/8/15.
 */
public class AppServeSetmealQvo extends CommConditionVo {
    private String content;
    private String type;
    private String roleType;
    private String hospId;
    private String areaCode;
    private String openArea;
    private String fwrq;//服务人群
    private String fwnr;//服务内容
    private String groupId;//组合主键

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

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
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

    public String getOpenArea() {
        return openArea;
    }

    public void setOpenArea(String openArea) {
        this.openArea = openArea;
    }

    public String getFwrq() {
        return fwrq;
    }

    public void setFwrq(String fwrq) {
        this.fwrq = fwrq;
    }

    public String getFwnr() {
        return fwnr;
    }

    public void setFwnr(String fwnr) {
        this.fwnr = fwnr;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
