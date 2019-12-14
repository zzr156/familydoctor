package com.ylz.bizDo.jtapp.patientVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * 注册
 */
public class AppPatientResgisterQvo extends CommConditionVo {
    private String userName;//用户名
    private String userIdNo;//身份证号
    private String userCard;//社保号
    private String userTel;//手机
    private String teamId;//团队主键
    private String drId;//医生主键
    private String userShort;//短信号
    private String password;//密码
    private String signpackageid;//套餐id
    private String userCrad;//错误社保卡
    private String flagFileUp;//是否已上传附件(0否 1是)
    private String signUpHpis;//签约来源
    private String quickState;//快捷状态
    private String equipmentNumber;//设备号
    private String equipmentName;//设备名称

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIdNo() {
        return userIdNo;
    }

    public void setUserIdNo(String userIdNo) {
        this.userIdNo = userIdNo;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserShort() {
        return userShort;
    }

    public void setUserShort(String userShort) {
        this.userShort = userShort;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignpackageid() {
        return signpackageid;
    }

    public void setSignpackageid(String signpackageid) {
        this.signpackageid = signpackageid;
    }

    public String getUserCrad() {
        return userCrad;
    }

    public void setUserCrad(String userCrad) {
        this.userCrad = userCrad;
    }

    public String getFlagFileUp() {
        return flagFileUp;
    }

    public void setFlagFileUp(String flagFileUp) {
        this.flagFileUp = flagFileUp;
    }

    public String getSignUpHpis() {
        return signUpHpis;
    }

    public void setSignUpHpis(String signUpHpis) {
        this.signUpHpis = signUpHpis;
    }

    public String getQuickState() {
        return quickState;
    }

    public void setQuickState(String quickState) {
        this.quickState = quickState;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}
