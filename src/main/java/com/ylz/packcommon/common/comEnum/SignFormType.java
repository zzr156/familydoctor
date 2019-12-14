package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/6/24.
 */
public enum SignFormType {

    /**
     * 0:预签约
     */
    YUQY("0"),
    /**
     * 1:待签约
     */
    DQY("1"),
    /**
     * 2:已签约
     */
    YQY("2"),
    /**
     * y3:解约中
     */
    JYZ("3"),
    /**
     * 4:已解约
     */
    YJY("4"),
    /**
     * 5:改签解约中
     */
    GQJYZ("5"),
    /**
     * 6:改签申请中
     */
    GQSQZ("6"),
    /**
     *7:已退约
     */
    YTY("7"),
    /**
     * 8拒签
     */
    JQ("8"),
    /**
     * 9删除
     */
    SC("9"),
    /**
     * 10变更
     */
    BG("10"),
    /**
     * 11转签
     */
    ZQ("11"),
    /**
     * 12撤回
     */
    CH("12"),
    /**
     * 98续签
     */
    XQ("98"),
    /**
     * 99预转签
     */
    YUZQ("99"),
    /**
     * web签约来源
     */
    WEBSTATE("2"),
    /**
     * app签约来源
     */
    APPSTATE("1"),
    /**
     * 微信签约来源
     */
    WECHATSTATE("3"),
    /**
     * 一体机
     */
    YITIJISTATE("4"),
    /**
     * POS机
     */
    POSSTATE("5"),

    /**
     * 随访
     */
    FOLLOWSTATE("999");
    private String value;
    private SignFormType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
