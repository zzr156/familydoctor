package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2019/1/28.
 */
public class AppMsgQvo extends CommConditionVo {
    private String id;//主键
    private String appName;//应用名
    private String appId;//应用id
    private String appKey;//应用key
    private String appMasterSecret;//应用主密钥
    private String appCreateTime;//应用创建时间
    private String appPgName;//应用包名
    private String appAreaCode;//地市区划

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPgName() {
        return appPgName;
    }

    public void setAppPgName(String appPgName) {
        this.appPgName = appPgName;
    }

    public String getAppCreateTime() {
        return appCreateTime;
    }

    public void setAppCreateTime(String appCreateTime) {
        this.appCreateTime = appCreateTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppMasterSecret() {
        return appMasterSecret;
    }

    public void setAppMasterSecret(String appMasterSecret) {
        this.appMasterSecret = appMasterSecret;
    }

    public String getAppAreaCode() {
        return appAreaCode;
    }

    public void setAppAreaCode(String appAreaCode) {
        this.appAreaCode = appAreaCode;
    }
}
