package com.ylz.bizDo.jtapp.wechatVo;

/**
 * @author dws
 */
public class WechatVo {

    private String code;    //换取access_token的票据
    private String state;   //重定向后会带上state参数,自定义
    private String openid;  //用户唯一标识
    private String access_token;    //网页授权接口调用凭证
    private String expires_in;  //access_token接口调用凭证超时时间，单位（秒）
    private String refresh_token;   //用户刷新access_token
    private String scope;   //用户授权的作用域，使用逗号（,）分
    private String appid;//小程序唯一标识
    private String secret;//小程序的 app secret

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
