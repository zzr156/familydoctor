package com.ylz.packcommon.common.comEnum;

/**
 * Created by zzl on 2017/8/7.
 */
public enum CommTcmTz {
    /**
     * 气虚质
     */
    QXZ("1"),
    /**
     * 阳虚质
     */
    YANGXZ("2"),
    /**
     * 阴虚质
     */
    YINXZ("3"),
    /**
     * 痰湿质
     */
    TSZ("4"),
    /**
     * 湿热质
     */
    SRZ("5"),
    /**
     * 血瘀质
     */
    XYZ("6"),
    /**
     * 气郁质
     */
    QYZ("7"),
    /**
     * 特禀质
     */
    TBZ("8"),
    /**
     * 平和质
     */
    PHZ("9");
    private String value;
    private CommTcmTz(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
