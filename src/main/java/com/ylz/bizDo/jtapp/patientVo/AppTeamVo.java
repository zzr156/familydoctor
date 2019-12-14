package com.ylz.bizDo.jtapp.patientVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/6/17.
 */
public class AppTeamVo extends CommConditionVo {
    private String teamHospId;//机构id
    private String teamName;//团队或医生名称
    private String teamSrc;//来源

    public String getTeamHospId() {
        return teamHospId;
    }

    public void setTeamHospId(String teamHospId) {
        this.teamHospId = teamHospId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamSrc() {
        return teamSrc;
    }

    public void setTeamSrc(String teamSrc) {
        this.teamSrc = teamSrc;
    }
}
