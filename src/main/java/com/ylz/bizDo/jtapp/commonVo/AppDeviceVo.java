package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/6/19.
 */
public class AppDeviceVo extends CommConditionVo {
    private String userId;//患者id
    private String type;//设备类型 1血压计 2血糖仪
    private String code;//SN码
    private String bindUser;//绑定人 1爸爸用户 2妈妈用户
    private String drId;//医生id
    private String userIdno1;//绑定用户1身份证号
    private String userIdno2;//绑定用户2身份证号
    private String teamId;//团队id
    private String devId;//设备id

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBindUser() {
        return bindUser;
    }

    public void setBindUser(String bindUser) {
        this.bindUser = bindUser;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getUserIdno1() {
        return userIdno1;
    }

    public void setUserIdno1(String userIdno1) {
        this.userIdno1 = userIdno1;
    }

    public String getUserIdno2() {
        return userIdno2;
    }

    public void setUserIdno2(String userIdno2) {
        this.userIdno2 = userIdno2;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }
}
