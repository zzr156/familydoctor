package com.ylz.packcommon.common.comEnum;

/**
 * 服务类型
 */
public enum ResidentMangeType {

    /**
     * 普通人群
     */
    PTRQ("1"),
    /**
     * 儿童(0~6岁)
     */
    ETLZLS("2"),
    /**
     * y孕产妇
     */
    YCF("3"),
    /**
     * 老年人
     */
    LNR("4"),
    /**
     * 高血压
     */
    GXY("5"),
    /**
     * 糖尿病
     */
    TNB("6"),
    /**
     *严重精神障碍
     */
    YZJSZY("7"),
    /**
     * 结核病
     */
    JHB("8"),
    /**
     * 残疾人
     */
    CJR("9"),
    /**
     * 其他
     */
    WEIZHI("99"),
    /**
     * 通用随访类型
     */
    TY("10"),
    /**
     * 脑血管病患者
     */
    NXGBHZ("10"),
    /**
     * 冠心病患者
     */
    GXBHZ("11"),
    /**
     * 癌症患者
     */
    AZHZ("12")
    ;
    private String value;
    private ResidentMangeType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
