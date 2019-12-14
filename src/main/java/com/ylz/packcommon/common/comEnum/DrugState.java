package com.ylz.packcommon.common.comEnum;

/**
 * Created by zzl on 2017/7/29.
 */
public enum DrugState  {
    /**
     * 无
     */
    WU("0"),
    /**
     * 有
     */
    YOU("1");


    private String value;
    private DrugState(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
