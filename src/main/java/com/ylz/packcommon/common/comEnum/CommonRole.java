package com.ylz.packcommon.common.comEnum;

/**
 * Created by zzl on 2017/6/27.
 */
public enum CommonRole  {

    /**
     * 管理员
     */
    GLY("2"),
    /**
     * 普通权限
     */
    PTQX("1");
    private String value;
    private CommonRole(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
