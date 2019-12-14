package com.ylz.bizDo.jtapp.commonEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * Created by asus on 2017/6/22.
 */
@XStreamAlias("response")
public class AppMessageEntityXml {
    @XStreamAlias("code")
    private String code;//
    @XStreamAlias("message")
    private AppMessageXml message;//正常应答

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AppMessageXml getMessage() {
        return message;
    }

    public void setMessage(AppMessageXml message) {
        this.message = message;
    }
}
