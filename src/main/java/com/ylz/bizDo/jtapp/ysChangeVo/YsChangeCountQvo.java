package com.ylz.bizDo.jtapp.ysChangeVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/9/4.
 */
public class YsChangeCountQvo extends CommConditionVo {
    private String type;//类型
    private String drId;//医生id
    private String teamId;//团队id
    private String drName;//医生名称
    private String hospId;//医院id
    private String patientId;//患者id
    private String changeDr;//变更医生id
    private String changeTeam;//变更医生团队
    private String changeType;//变更类型（0全选医生在此团队下的所有患者 1）
    private String patientName;//患者姓名模糊查询
    private String labelType;//标签类型（3服务人群、2疾病类型、1健康情况）
    private String changeDrAssistantId;
    private String batchOperatorId;//签约操作者ID
    private String batchOperatorName;//签约操作者

    public String getChangeDrAssistantId() {
        return changeDrAssistantId;
    }

    public void setChangeDrAssistantId(String changeDrAssistantId) {
        this.changeDrAssistantId = changeDrAssistantId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
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
}
