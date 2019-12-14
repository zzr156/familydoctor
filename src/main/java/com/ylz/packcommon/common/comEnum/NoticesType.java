package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/6/20.
 */
public enum NoticesType {

    /**
     * 用药指导
     */
    YYZD("0"),
    /**
     * 体检提醒
     */
    TJTX("1"),
    /**
     * 预防接种提醒
     */
    YFJZTX("2"),
    /**
     * 健康检测异常提醒
     */
     JKJCYCTX("3"),
    /**
     * 系统消息
     */
    XTXX("4"),
    /**
     * 咨询消息
     */
    ZXXX("5"),
    /**
     * 签约消息
     */
    QYXX("6"),
    /**
     *体征指标预警
     */
    TZZSYJ("7"),
    /**
     * 工作计划提醒
     */
    GZJHTX("8"),
    /**
     * 用药短缺预警
     */
    YYDQJJ("9"),
    /**
     * 居家养老
     */
    JJYL("10"),
    /**
     * 推送健康资讯
     */
    JKZX("11"),
    /**
     * 呼叫信息
     */
    HJXX("12"),
    /**
     * 健康指导
     */
    JKZD("13"),
    /**
     * 随访提醒
     */
    SFTX("14"),
    /**
     * 家庭签约
     */
    JTQY("15"),
    /**
     * 用药提醒
     */
    YYTX("16"),
    /**
     * 中医药保健指导
     */
    ZYYBJZD("17"),
    /**
     * 变更医生
     */
    BGYS("18"),
    /**
     * 建档消息
     */
    JDXX("19"),
    /**
     * 绩效消息
     */
    JXXX("20");
    private String value;
    private NoticesType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
