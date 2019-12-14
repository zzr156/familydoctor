package com.ylz.packcommon.common.comEnum;

/**工作计划状态
 * Created by zzl on 2017/6/28.
 */
public enum CommonWorkPlanState {
    /**
     * 未完成
     */
    WWC("1"),
    /**
     * 已完成
     */
    YWC("2");
    private String value;
    private CommonWorkPlanState(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
