package com.ylz.packcommon.common.comEnum;

/**
 * 签约方式
 * 0代表自己 1代表家人代签 2代表医生代签
 * Created by zzl on 2017/6/28.
 */
public enum SignFormWay {
    /**
     * 代表自己
     */
    DBZJ("0"),
    /**
     * 代表家人
     */
    DBJR("1"),
    /**
     * 代表医生
     */
    DBYS("2");
    private String value;
    private SignFormWay(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
