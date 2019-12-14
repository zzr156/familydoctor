package com.ylz.packcommon.common.comEnum;

/**
 * Created by zzl on 2017/7/29.
 */
public enum FollowThisType {
    /**
     * 控制满意
     */
    KZMY("1"),
    /**
     * 控制不满意
     */
    KZBMY("2"),
    /**
     * 不良反应
     */
    BLFY("3"),
    /**
     *  并发症
     */
   BFZ("4"),
    /**
     * 不稳定
     */
    BWD("1"),
    /**
     * 基本稳定
     */
    JBWD("2"),
    /**
     * 稳定
     */
    WD("3");
    private String value;
    private FollowThisType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
