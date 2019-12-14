package com.ylz.packcommon.common.comEnum;

/**
 * 转诊类型
 * Created by zzl on 2017/12/11.
 */
public enum ReferralType  {
    /**
     * 转出
     */
    ZC("1"),
    /**
     * 转入
     */
    ZR("2");
    private String value;
    private ReferralType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
