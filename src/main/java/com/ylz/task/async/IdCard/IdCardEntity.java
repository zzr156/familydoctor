package com.ylz.task.async.IdCard;

/**
 * Created by asus on 2017/7/5.
 */
public class IdCardEntity {
    private String areaCode;//区域编码
    private String idCard;//身份证
    private String ssCard;//社保卡
    private String status;//状态
    private String statusCode;//状态Code
    private String userName;//姓名



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSsCard() {
        return ssCard;
    }

    public void setSsCard(String ssCard) {
        this.ssCard = ssCard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
