package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/6/16.
 */
public enum CommonLoginType {

    /**
     * 智能客厅
     */
    ZNKT("5"),
    /**
     * 微信OPEN
     */
    WEIXINOPEN("2"),
    /**
     * 身份证或账号
     */
    ZHANGHAO("0"),

    /**
     * 手机
     */
    SHOUJI("1"),
    /**
     * 医生主键登入
     */
    YSZJ("3"),
    /**
     * 居民主键登入
     */
    JMZJ("4");
    private String value;
    private CommonLoginType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
