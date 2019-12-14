package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by asus on 2017/7/5.
 */
public class AppEaseEntity {
    private String userId;
    private String userName;
    private String userImageUrl;
    private String userGender;
    private String userImageName;
    private String userType;//用户类型

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserImageName() {
        return userImageName;
    }

    public void setUserImageName(String userImageName) {
        this.userImageName = userImageName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
