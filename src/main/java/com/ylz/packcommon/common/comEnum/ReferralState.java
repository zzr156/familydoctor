package com.ylz.packcommon.common.comEnum;

/**
 * 转诊状态
 * Created by zzl on 2017/12/11.
 */
public enum ReferralState {
    /**
     * 转诊待通过
     */
    ZZDTG("0"),
    /**
     * 转诊已通过
     */
    ZZYTG("1"),
    /**
     * 转诊已过期
     */
    ZZYGQ("2"),
    /**
     * 转诊已拒绝
     */
    ZZYJJ("3"),
    /**
     * 终止
     */
    ZZ("4"),
    /**
     * 康复回转
     */
    KFHZ("5");
    private String value;
    private ReferralState(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
