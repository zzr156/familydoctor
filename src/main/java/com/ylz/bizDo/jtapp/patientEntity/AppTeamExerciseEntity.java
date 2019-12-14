package com.ylz.bizDo.jtapp.patientEntity;

/**
 * Created by zzl on 2017/6/17.
 */
public class AppTeamExerciseEntity {
    private String teamName;//团队名称
    private String teamId;//团队id
    private String hospId;//医院主键
    private String areaCode;//行政区划
    private String drId;//医生主键

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
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

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }
}
