package com.ylz.bizDo.jtapp.ysChangeVo;

/**
 * Created by zzl on 2017/9/5.
 */
public class YsChangeSureQvo {
    private String changeDr;//变更医生id
    private String changeTeam;//变更医生团队id
    private String time;//时间
    private String drId;//申请医生id
    private String teamId;//申请医生团队id
    private String changeType;//同意或拒绝（1同意 2拒绝）
    private String num;//变更编号
    private String reason;//拒绝理由
    private String drName;//医生姓名
    private String changeDrAssistantId;
    private String upHpis;//签约来源
    private String batchOperatorId;//签约操作者ID
    private String batchOperatorName;//签约操作者
    private String changeOperatorId;//变更操作人id
    private String changeOperatorName;//变更操作人姓名

    public String getChangeDrAssistantId() {
        return changeDrAssistantId;
    }

    public void setChangeDrAssistantId(String changeDrAssistantId) {
        this.changeDrAssistantId = changeDrAssistantId;
    }

    public String getChangeDr() {
        return changeDr;
    }

    public void setChangeDr(String changeDr) {
        this.changeDr = changeDr;
    }

    public String getChangeTeam() {
        return changeTeam;
    }

    public void setChangeTeam(String changeTeam) {
        this.changeTeam = changeTeam;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getUpHpis() {
        return upHpis;
    }

    public void setUpHpis(String upHpis) {
        this.upHpis = upHpis;
    }

    public String getBatchOperatorId() {
        return batchOperatorId;
    }

    public void setBatchOperatorId(String batchOperatorId) {
        this.batchOperatorId = batchOperatorId;
    }

    public String getBatchOperatorName() {
        return batchOperatorName;
    }

    public void setBatchOperatorName(String batchOperatorName) {
        this.batchOperatorName = batchOperatorName;
    }

    public String getChangeOperatorId() {
        return changeOperatorId;
    }

    public void setChangeOperatorId(String changeOperatorId) {
        this.changeOperatorId = changeOperatorId;
    }

    public String getChangeOperatorName() {
        return changeOperatorName;
    }

    public void setChangeOperatorName(String changeOperatorName) {
        this.changeOperatorName = changeOperatorName;
    }
}
