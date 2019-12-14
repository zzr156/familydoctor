package com.ylz.bizDo.jtapp.drVo;

/**
 * Created by asus on 2017/6/24.
 */
public class AppDrCount {
    private String teamId;//团队主键
    private String patientId;
    private String drName;


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

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }
}
