package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/6/24.
 */
public enum FollowPlanState {

    /**
     * 1:未完成
     */
    WEIWANCHENG("0"),
    /**
     * 2:已完成
     */
    YIWANCHENG("1"),
    /**
     * 过期
     */
    GUOQI("2"),
    /**
     * y3:取消
     */
    QUXIAO("3"),
    /**
     * 延期
     */
    YANQI("4"),
    /**
     * 死亡
     */
    SIWANG("5"),
    /**
     * 失访
     */
    SHIFANG("6");

    private String value;
    private FollowPlanState(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
