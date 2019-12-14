package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017-7-13.
 */
public enum AppFlagAgree {

    /**
     * 开启
     */
    KAIQI("1"),
    /**
     * 关闭
     */
    GUANBI("0");

    private String value;
    private AppFlagAgree(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
