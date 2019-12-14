package com.ylz.packcommon.common.comEnum;

/**
 * 健康档案类型
 */
public enum HealthInfoType {





    /**
     * 健康档案列表
     */
    JKDALB("0"),
    /**
     * 健康档案列表体检
     */
    JKDALBTJ("1");
    private String value;
    private HealthInfoType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
