package com.ylz.packcommon.integrate.sdk.bean;

import java.io.Serializable;

/**
 * Created by asus on 2019/01/28.
 */
public class RequestParams implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appId;
    private Object param;
    private String sign;
    private String code;
    private String timestamp;
    private String type;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
