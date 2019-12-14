package com.ylz.packcommon.common.comEnum;

/** 中医药保健指导
 * Created by zzl on 2017/8/7.
 */
public enum CommTcmGuide {
    /**
     * 情志调摄
     */
    QZTS("901"),
    /**
     * 饮食调养
     */
    YSTY("902"),
    /**
     * 起居调摄
     */
    QJTS("903"),
    /**
     * 运动保健
     */
    YDBJ("904"),
    /**
     * 穴位保健
     */
    XWBJ("905"),
    /**
     * 其他
     */
    QT("906");

    private String value;
    private CommTcmGuide(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
