package com.ylz.bizDo.plan.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * 随访view接收类型
 */
public class AppFollwCommQvo extends CommConditionVo{
    private String id;//随访id
    private String patientId;//患者id
    private String persGroup;//服务人群类型

    public String getPersGroup() {
        return persGroup;
    }

    public void setPersGroup(String persGroup) {
        this.persGroup = persGroup;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
