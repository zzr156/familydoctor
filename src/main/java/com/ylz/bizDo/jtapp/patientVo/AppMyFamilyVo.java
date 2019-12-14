package com.ylz.bizDo.jtapp.patientVo;

/**
 * 我的家庭
 */
public class AppMyFamilyVo {
    private String userIdNo;//身份证号
    private String nickCode;//昵称
    private String userName;//姓名
    private String mykh;//免疫卡号
    private String familyId;//主键
    private String type;//类型
    private String userId;//当前用户id(健康三明)
    private String idCard;//当前用户身份证号(健康三明)
    private String userCard;//社保卡

    public String getUserIdNo() {
        return userIdNo;
    }

    public void setUserIdNo(String userIdNo) {
        this.userIdNo = userIdNo;
    }

    public String getNickCode() {
        return nickCode;
    }

    public void setNickCode(String nickCode) {
        this.nickCode = nickCode;
    }

    public String getMykh() {
        return mykh;
    }

    public void setMykh(String mykh) {
        this.mykh = mykh;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }
}
