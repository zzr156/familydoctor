package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/6/16.
 */
public enum CommonDrPartientType {

    /**
     * 医生
     */
    yisheng("2"),

    /**
     * 患者
     */
    huanzhe("1");
    private String value;
    private CommonDrPartientType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
