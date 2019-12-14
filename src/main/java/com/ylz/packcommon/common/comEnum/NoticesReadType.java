package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/6/20.
 */
public enum NoticesReadType {
    /**
     * 已读
     */
    YIDU("1"),
    /**
     * 未读
     */
    WEIDU("0");
    private String value;
    private NoticesReadType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
