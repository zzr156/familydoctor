package com.ylz.bizDo.jtapp.gaiRuiVo;

/**
 * Created by zzl on 2019/3/25.
 */
public class GaiRuiVo {
    private String appId;//APPID
    private String orgId;//社区ID
    private String deviceId;//设备系列号
    private String drId;//操作用户ID
    private String token;//token

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
