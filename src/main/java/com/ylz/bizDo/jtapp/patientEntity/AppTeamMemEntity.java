package com.ylz.bizDo.jtapp.patientEntity;

/**
 * Created by zzl on 2018/5/11.
 */
public class AppTeamMemEntity {
    private String id;
    private String memTeamid;//团队主键
    private String memDrId;//成员主键
    private String memDrName;//成员名称
    private String memWorkType;//成员工作类型 1..健康管理师,2.专科医生,3.全科医生,4.医技人员,5.公卫医师,6.社区护士
    private String memTeamName;//团队名称
    private String memState;// 团队角色 0：队长 1：成员
    private String drRole;//成员职称 1管理员 2专科医生 3全科医生 4医技人员 5公卫医师 6社区护士 7助理
    private String drTel;//电话号码
    private String drGender;//性别 1男 2女

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemTeamid() {
        return memTeamid;
    }

    public void setMemTeamid(String memTeamid) {
        this.memTeamid = memTeamid;
    }

    public String getMemDrId() {
        return memDrId;
    }

    public void setMemDrId(String memDrId) {
        this.memDrId = memDrId;
    }

    public String getMemDrName() {
        return memDrName;
    }

    public void setMemDrName(String memDrName) {
        this.memDrName = memDrName;
    }

    public String getMemWorkType() {
        return memWorkType;
    }

    public void setMemWorkType(String memWorkType) {
        this.memWorkType = memWorkType;
    }

    public String getMemTeamName() {
        return memTeamName;
    }

    public void setMemTeamName(String memTeamName) {
        this.memTeamName = memTeamName;
    }

    public String getMemState() {
        return memState;
    }

    public void setMemState(String memState) {
        this.memState = memState;
    }

    public String getDrRole() {
        return drRole;
    }

    public void setDrRole(String drRole) {
        this.drRole = drRole;
    }

    public String getDrTel() {
        return drTel;
    }

    public void setDrTel(String drTel) {
        this.drTel = drTel;
    }

    public String getDrGender() {
        return drGender;
    }

    public void setDrGender(String drGender) {
        this.drGender = drGender;
    }
}
