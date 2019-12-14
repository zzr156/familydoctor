package com.ylz.packcommon.integrate.sdk.enums;

/**
 * Created by asus on 2017/6/16.
 */
public enum HtmlType {
    /**
     * 在线建档
     */
    ZXJD("ZXJD"),

    /**
     * 签约申请
     */
    QYSQ("QYSQ"),
    /**
     * 中医体质辨识
     */
    ZYTZBS("ZYTZBS"),
    /**
     * 健康监测
     */
    JKJC("JKJC");
    private String value;
    private HtmlType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
