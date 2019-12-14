package com.ylz.packcommon.common.comEnum;

/**
 * Created by zzl on 2017/10/20.
 */
public enum NoticesMType  {
    /**
     * 续签
     */
    XQXX("1");
    private String value;
    private NoticesMType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
