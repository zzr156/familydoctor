package com.ylz.packcommon.common.comEnum;

/**
 * Created by zzl on 2017/7/10.
 */
public enum CommonWorkType  {
    /**
     *  1 健康管理师
     */
    JKGLS("1"),
    /**
     *  2 专科医生
     */
    ZKYS("2"),
    /**
     * 3 全科医生
     */
    QKYS("3"),
    /**
     * 4 医技人员
     */
    YJRY("4"),
    /**
     * 5 公卫医师
     */
    GWYS("5"),
    /**
     * 6 社区护士
     */
    SQHS("6");
    private String value;
    private CommonWorkType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
