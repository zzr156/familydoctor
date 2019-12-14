package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/7/5.
 */
public enum UserUpHpisType {
    /**
     * 0 激活
     */
    JIHUO("0"),
    /**
     * 1 未激活
     */
    WEIJIHUO("1");
    private String value;
    private UserUpHpisType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
