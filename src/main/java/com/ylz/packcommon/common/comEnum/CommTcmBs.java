package com.ylz.packcommon.common.comEnum;

/** 体质辨识
 * Created by zzl on 2017/8/7.
 */
public enum CommTcmBs {
    /**
     * 是
     */
    SHI("1"),
    /**
     * 倾向是
     */
    QXS("2"),
    /**
     * 否
     */
    FOU("3");
    private String value;
    private CommTcmBs(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
