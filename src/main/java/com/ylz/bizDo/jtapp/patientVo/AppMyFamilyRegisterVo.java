package com.ylz.bizDo.jtapp.patientVo;

/**
 * 我的家庭
 */
public class AppMyFamilyRegisterVo {
    private String userIdNo;//身份证号
    private String userName;//昵称
    private String userCrad;//社保卡
    private String userTel;//手机号
    private String nickName;//昵称
    private String userMykh;//免疫卡号

    public String getUserIdNo() {
        return userIdNo;
    }

    public void setUserIdNo(String userIdNo) {
        this.userIdNo = userIdNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCrad() {
        return userCrad;
    }

    public void setUserCrad(String userCrad) {
        this.userCrad = userCrad;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserMykh() {
        return userMykh;
    }

    public void setUserMykh(String userMykh) {
        this.userMykh = userMykh;
    }
}
