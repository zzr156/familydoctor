package com.ylz.bizDo.plan.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**随访
 * Created by zzl on 2017/6/29.
 */
public class AppFollowAddQvo extends CommConditionVo {
    private String patientId;//多个用分割隔开
    private String followWay;//随访方式
    private String followState;//随访状态
    private String teamId;
    private String followDate;//随访计划时间
    private String isTemporary;//是否是临时随访（0否 1是）
    private String sfRemindPlan;//提醒方案
    private String followId;//随访主表id

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }


    public String getFollowWay() {
        return followWay;
    }

    public void setFollowWay(String followWay) {
        this.followWay = followWay;
    }

    public String getFollowState() {
        return followState;
    }

    public void setFollowState(String followState) {
        this.followState = followState;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getFollowDate() {
        return followDate;
    }

    public void setFollowDate(String followDate) {
        this.followDate = followDate;
    }

    public String getIsTemporary() {
        return isTemporary;
    }

    public void setIsTemporary(String isTemporary) {
        this.isTemporary = isTemporary;
    }

    public String getSfRemindPlan() {
        return sfRemindPlan;
    }

    public void setSfRemindPlan(String sfRemindPlan) {
        this.sfRemindPlan = sfRemindPlan;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }
}
