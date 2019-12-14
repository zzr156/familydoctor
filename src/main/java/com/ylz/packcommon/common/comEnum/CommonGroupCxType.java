package com.ylz.packcommon.common.comEnum;

/** 组合查询条件枚举
 * Created by zzl on 2017/8/15.
 */
public enum CommonGroupCxType {
    /**
     * 服务内容
     */
    FWNR("1"),
    /**
     * 服务人群
     */
    FWRQ("2"),
    /**
     * 套餐名称
     */
    TCMC("3");
    private String value;
    private CommonGroupCxType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
