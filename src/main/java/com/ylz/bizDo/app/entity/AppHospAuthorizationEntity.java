package com.ylz.bizDo.app.entity;

/**
 * Created by lenovo on 2017/12/22.
 */
public class AppHospAuthorizationEntity {

    private String areaCode;//区域编号
    private String type;// 用于判断区县授权或机构
    private String areaValue;//
    private String codenockde;
    private String id;
    private String imageBase;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAreaValue() {
        return areaValue;
    }

    public void setAreaValue(String areaValue) {
        this.areaValue = areaValue;
    }

    public String getCodenockde() {
        return codenockde;
    }

    public void setCodenockde(String codenockde) {
        this.codenockde = codenockde;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageBase() {
        return imageBase;
    }

    public void setImageBase(String imageBase) {
        this.imageBase = imageBase;
    }
}
