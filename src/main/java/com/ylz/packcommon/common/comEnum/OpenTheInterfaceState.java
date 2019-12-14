package com.ylz.packcommon.common.comEnum;

/**
 * 是否开启接口
 * Created by zzl on 2018/4/13.
 */
public enum OpenTheInterfaceState {
    /**
     * 是
     */
    YES("1"),
    /**
     * 否
     */
    NOT("0");
    private String value;
    private OpenTheInterfaceState(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
