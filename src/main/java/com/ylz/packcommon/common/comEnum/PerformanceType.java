package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/11/06.
 */
public enum PerformanceType {
    /**
     * 居民健康档案
     */
    JMJKDA("1"),

    /**
     *  健康教育
     */
    JKJY("2"),

    /**
     * 健康指导
     */
    JKZD("3"),

    /**
     * 定期随访
     */
    DQSF("4"),

    /**
     * 健康咨询
     */
    JKZX("5"),

    /**
     * 预防接种
     */
    YFJZ("6"),

    /**
     * 中医药健康指导（0-36月龄）
     */
    ZYYJKZD("7"),

    /**
     * 孕期保健服务
     */
    YQBJFW("8"),

    /**
     * 产后视访
     */
    CHFS("9"),

    /**
     * 中医体质辨识
     */
    ZYTZBS("10"),

    /**
     * 中医药保健指导
     */
    ZYYBJZD("11"),

    /**
     * 健康体检
     */
    JKTJ("12"),

    /**
     * 用药指导
     */
    YYZD("13"),
    /**
     * 公共服务
     */
    GGFW("14");
    private String value;
    private PerformanceType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
