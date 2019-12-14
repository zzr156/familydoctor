package com.ylz.bizDo.jtapp.drEntity;

/**
 * Created by asus on 2017/6/19.
 */
public class AppDrChangeInfoEntity {
    private String id;//主键
    private String drName;//医生名称
    private String drGender;//医生性别
    private String drImageUrl;//医生头像
    private String drImageName;//头像文件名
    private String drIntro;//医生简介
    private String drGood;//医生擅长


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrGender() {
        return drGender;
    }

    public void setDrGender(String drGender) {
        this.drGender = drGender;
    }

    public String getDrImageUrl() {
        return drImageUrl;
    }

    public void setDrImageUrl(String drImageUrl) {
        this.drImageUrl = drImageUrl;
    }

    public String getDrImageName() {
        return drImageName;
    }

    public void setDrImageName(String drImageName) {
        this.drImageName = drImageName;
    }

    public String getDrIntro() {
        return drIntro;
    }

    public void setDrIntro(String drIntro) {
        this.drIntro = drIntro;
    }

    public String getDrGood() {
        return drGood;
    }

    public void setDrGood(String drGood) {
        this.drGood = drGood;
    }
}
