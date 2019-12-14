package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/6/24.
 */
public enum EvaluationType {

    /**
     * 1好评
     */
    HP("1"),
    /**
     * 2:中评
     */
    ZP("2"),
    /**
     * 3:差评
     */
    CP("3");
    private String value;
    private EvaluationType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
