package com.ylz.bizDo.plan.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**随访
 *
 */
public class AppFollowGroupQvo extends CommConditionVo {
    private String group;//服务类型，多个用分号分割
    private String drId;//医生id
    private String patientName;//患者姓名（搜索条件）
    private String day;//0今天，1未来三天，2未来七天，3未来一个月，4未来三个月，-1未完成
    private String startDate;//开始时间
    private String endDate;//结束时间
    private String patientId;//患者id
    private String date;//年份
    private String teamId;//团队id
    private String signState;//签约状态

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }
}
