package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/6/17.
 */
public enum CommonUseType {


    /**
     * 系统
     */
    XITONG("1"),
    /**
     * 个人
     */
    GEREN("2");
    private String value;
    private CommonUseType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
