package com.ylz.bizDo.jtapp.patientVo;

import java.util.List;

/**
 * Created by zzl on 2017/12/29.
 */
public class AppFamilySignQvo {
    private String teamId;//团队id
    private String drId;//医生id
    private String patientId;//户主id
    private String type;//签约类型 1个人签约 2代签约
    private List<AppFamilySignSonQvo> sonList;//

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public List<AppFamilySignSonQvo> getSonList() {
        return sonList;
    }

    public void setSonList(List<AppFamilySignSonQvo> sonList) {
        this.sonList = sonList;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
