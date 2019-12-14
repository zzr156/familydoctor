package com.ylz.bizDo.message.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by hzk on 2017/7/4.
 */
public class MessageQvo extends CommConditionVo {
    private String teamId;//团队id
    private String patientId;//患者id
    private String name;//姓名
    private String drId;//医生主键

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }
}
