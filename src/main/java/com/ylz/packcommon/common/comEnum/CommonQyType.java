package com.ylz.packcommon.common.comEnum;

/**
 * Created by dws on 2017-05-13.
 */
public enum CommonQyType {

    /**
     * 拒绝解约
     */
    JJJY("09"),
    /**
     * 已退约
     */
    YTY("07"),
    /**
     * 已解约
     */
    YJY("04"),

    /**
     * 待解约
     */
    DJY("03"),

    /**
     * 已签约
     */
    YQY("02"),

    /**
     * 待签约
     */
    DQY("01");
    private String value;
    private CommonQyType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
