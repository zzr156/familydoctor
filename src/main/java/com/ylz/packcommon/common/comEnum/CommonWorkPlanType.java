package com.ylz.packcommon.common.comEnum;

/**工作计划
 * Created by zzl on 2017/6/28.
 */
public enum CommonWorkPlanType {
    /**
     * 随访计划
     */
    SFJH("1"),
    /**
     * 干预
     */
    GY("2");
    private String value;
    private CommonWorkPlanType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
