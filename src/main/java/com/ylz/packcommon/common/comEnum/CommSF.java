package com.ylz.packcommon.common.comEnum;

/**
 * Created by zzl on 2017/7/13.
 */
public enum CommSF  {
    /**
     *  1 是
     */
    YES("1"),
    /**
     *  2 否
     */
    NOT("0");
    private String value;
    private CommSF(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
