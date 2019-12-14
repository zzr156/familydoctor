package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/10/23.
 */
public enum EconomicType {

    /**
     * 一般人口
     */
    YBRK("1"),
    /**
     * 建档立卡贫困人口
     */
    JDLKPKRK("2"),
    /**
     * 低保户
     */
    DBH("3"),
    /**
     * 特困户
     */
    TKH("4"),
    /**
     * 计生独伤残家庭
     */
    JSTSJT("5"),
    /**
     * 计生独子女户
     */
    JSDZNH("7"),
    /**
     * 计生双女户
     */
    JSSNH("8"),
    /**
     * 贫困户
     */
    PKH("9"),
    /**
     * 其他
     */
    QT("10");
    private String value;
    private EconomicType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
