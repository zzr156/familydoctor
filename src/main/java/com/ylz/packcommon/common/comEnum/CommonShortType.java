package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/6/16.
 */
public enum CommonShortType {
    /**
     * 智能客厅
     */
    ZNKT("5"),

    /**
     * 绑定手机
     */
    BANGDINGTEL("4"),
    /**
     * 注册
     */
    ZHUCE("3"),
    /**
     * 医生
     */
    YISHENG("2"),

    /**
     * 患者
     */
    HUANGZHE("1");
    private String value;
    private CommonShortType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
