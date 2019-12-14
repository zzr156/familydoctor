package com.ylz.packcommon.common.comEnum;

/**
 * Created by zzl on 2017/8/10.
 */
public enum CommonGuideType {
    JKGY("!"),//健康干预
    JKZD("2"),//健康指导
    ZYYZD("3");//中医药健康指导
    private String value;
    private CommonGuideType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
