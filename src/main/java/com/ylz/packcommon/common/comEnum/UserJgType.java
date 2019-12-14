package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/7/5.
 */
public enum UserJgType {
    /**
     * 0 未设置
     */
    WEISHEZHI("0"),
    /**
     * 1 已设置
     */
    YISHEZHI("1");
    private String value;
    private UserJgType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
