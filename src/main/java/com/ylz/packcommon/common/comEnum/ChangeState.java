package com.ylz.packcommon.common.comEnum;

/**
 * 变更状态枚举
 * Created by zzl on 2017/9/5.
 */
public enum ChangeState {
    /**
     * 申请变更
     */
    SQBG("1"),
    /**
     * 同意变更
     */
    TYBG("2"),
    /**
     * 拒绝变更
     */
    JJBG("3"),
    /**
     * 变更过期
     */
    BGGQ("4");
    private String value;
    private ChangeState(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
