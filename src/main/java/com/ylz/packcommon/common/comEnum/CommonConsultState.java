package com.ylz.packcommon.common.comEnum;

/**
 * 咨询状态
 * Created by zzl on 2017/6/26.
 */
public enum CommonConsultState {
    /**
     * 待回复
     */
    DHF("0"),
    /**
     * 进行中
     */
    JXZ("1"),
    /**
     * 已完成
     */
    YWC("2");
    private String value;
    CommonConsultState(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
