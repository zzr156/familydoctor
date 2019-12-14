package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/6/24.
 */
public enum TeamType {

    /**
     * 1:家签
     */
    JTQY("0"),
    /**
     * 2:三师
     */
    SSGG("1"),
    /**
     * y3:居家养老
     */
    JJYL("2");
    private String value;
    private TeamType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
