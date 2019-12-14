package com.ylz.packcommon.common.comEnum;

/**
 * Created by zzl on 2018/7/24.
 */
public enum SourceType {
    /**
     * app
     */
    APP("1"),
    /**
     * 智能设备
     */
    ZNSB("2"),
    /**
     * 随访
     */
    SF("3");
    private String value;
    private SourceType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
