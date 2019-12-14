package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by zzl on 2017/6/19.
 */
public class AppDeviceEntity {
    private String id;//设备id
    private String devName;//设备名称
    private String devImageUrl;//设备图片
    private String devFactoryModel;//厂家型号
    private String devType;//设备类型

    public String getId() {
        return id;
    }

    public String getDevName() {
        return devName;
    }

    public String getDevImageUrl() {
        return devImageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public void setDevImageUrl(String devImageUrl) {
        this.devImageUrl = devImageUrl;
    }

    public String getDevFactoryModel() {
        return devFactoryModel;
    }

    public void setDevFactoryModel(String devFactoryModel) {
        this.devFactoryModel = devFactoryModel;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }
}
