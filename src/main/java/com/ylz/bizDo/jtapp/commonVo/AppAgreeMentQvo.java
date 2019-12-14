package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by asus on 2017/6/17.
 */
public class AppAgreeMentQvo extends CommConditionVo {
    private String teamId;// 团队主键
    private String patientId;// 患者主键
    private String signId;// 签约单主键
    private String type;// 类型（1-系统，2-个人）
    private String qyType;//签约类型（1-个人，2-家庭）
    private String state;//查看状态（1-申请签约时查看协议，2-已签约状态查看协议）
    private String drId;// 医生ID
    private String wechatType;// 微信类型


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

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQyType() {
        return qyType;
    }

    public void setQyType(String qyType) {
        this.qyType = qyType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getWechatType() {
        return wechatType;
    }

    public void setWechatType(String wechatType) {
        this.wechatType = wechatType;
    }
}
