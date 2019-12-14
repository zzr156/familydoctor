package com.ylz.packcommon.common.comEnum;

/**
 * 社保卡状态枚举
 * Created by zzl on 2018/9/25.
 */
public enum IdCardStatus {
    /**
     * 停用
     */
    TY("0"),
    /**
     * 正常
     */
    ZC("1"),
    /**
     * 挂失
     */
    GS("2"),
    /**
     * 注销
     */
    ZX("9");
    private String value;
    private IdCardStatus(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
