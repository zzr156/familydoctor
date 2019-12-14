package com.ylz.bizDo.jtapp.patientVo;

/**转签请求参数
 * Created by zzl on 2017/10/26.
 */
public class AppGoToSignQvo {
    private String signId;//签约单Id
    private String reason;//转签原因
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String drId;//医生id
    private String teamId;//团队Id
    private String patientId;//患者id
    private String signUpHpis;//签约来源
    private String signpackageid;//服务包主键

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
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

    public String getSignUpHpis() {
        return signUpHpis;
    }

    public void setSignUpHpis(String signUpHpis) {
        this.signUpHpis = signUpHpis;
    }

    public String getSignpackageid() {
        return signpackageid;
    }

    public void setSignpackageid(String signpackageid) {
        this.signpackageid = signpackageid;
    }
}
