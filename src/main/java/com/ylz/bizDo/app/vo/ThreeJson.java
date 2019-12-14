package com.ylz.bizDo.app.vo;

/**
 * Created by zzl on 2018/5/30.
 */
public class ThreeJson {
    private String code = "1";//响应码,1成功,2拒收(不重发),其他失败(会重新发送)
    private String status = "success";//状态:success为成功,其他为失败
    private String msg;//个性化响应消息

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
