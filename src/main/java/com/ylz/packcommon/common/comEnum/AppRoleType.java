package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017-7-12.
 */
public enum AppRoleType {

    /**
     * 省
     */
    SHENG("1"),
    /**
     * 市
     */
    SHI("2"),
    /**
     * 区
     */
    QU("3"),
    /**
     * 社区
     */
    SHEQU("4"),

    /**
     * 团队
     */
    TUANDUI("8"),
    /**
     * 医生
     */
    YISHENG("9"),
    /**
     * 建档立卡
     */
    JDLK("5");


    private String value;
    private AppRoleType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
