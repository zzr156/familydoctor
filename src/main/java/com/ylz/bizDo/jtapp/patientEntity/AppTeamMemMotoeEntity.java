package com.ylz.bizDo.jtapp.patientEntity;

/**
 * Created by zzl on 2018/5/11.
 */
public class AppTeamMemMotoeEntity {
    private String drId;//成员主键
    private String drWorkTitle;//成员工作类型 1..健康管理师,2.专科医生,3.全科医生,4.医技人员,5.公卫医师,6.社区护士
    private String teamRole;// 团队角色 0：队长 1：成员
    private String drWorkType;//工作类型 实习医师', '进修医师', '住院医师', '主治医师', '副主任医师', '主任医师', '科主任', '住院总医师
    private String drWorkDuty;//工作职责


    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrWorkTitle() {
        return drWorkTitle;
    }

    public void setDrWorkTitle(String drWorkTitle) {
        this.drWorkTitle = drWorkTitle;
    }

    public String getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(String teamRole) {
        this.teamRole = teamRole;
    }

    public String getDrWorkType() {
        return drWorkType;
    }

    public void setDrWorkType(String drWorkType) {
        this.drWorkType = drWorkType;
    }

    public String getDrWorkDuty() {
        return drWorkDuty;
    }

    public void setDrWorkDuty(String drWorkDuty) {
        this.drWorkDuty = drWorkDuty;
    }
}
