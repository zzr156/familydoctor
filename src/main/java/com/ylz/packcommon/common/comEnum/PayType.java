package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/6/28.
 */
public enum PayType {

    /**
     * 已缴费
     */
    YIJIAOFEI("1"),

    /**
     * 未缴费
     */
    WEIJIAOFEI("0");
    private String value;
    private PayType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
