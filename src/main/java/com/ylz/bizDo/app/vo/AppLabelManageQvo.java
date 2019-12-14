package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * 标签请求参数
 * Created by zzl on 2017/6/17.
 */
public class AppLabelManageQvo extends CommConditionVo {
    private String orgId;//机构主键
    private String type;//类型
    private String role;//权限
    private String drId;//医生主键
    private String name;//姓名
    private String idno;//身份证号码

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }
}
