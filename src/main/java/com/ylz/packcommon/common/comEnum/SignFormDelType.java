package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/08/21.
 */
public enum SignFormDelType {

    /**
     * 1:死亡
     */
    SW("1"),
    /**
     * 2:其他
     */
    QT("2");
    private String value;
    private SignFormDelType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
