package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/7/7.
 */
public enum AddressType {
    /**
     * 1.福州,2泉州,3漳州,4.莆田,5.南平,6.三明,7.测试
     * 福州
     */
    FZ("0591"),
    /**
     * 泉州
     */
    QZ("0595"),
    /**
     * 漳州
     */
    ZZ("0596"),
    /**
     * 莆田
     */
    PT("0594"),
    /**
     * 南平
     */
    NP("0599"),
    /**
     * 三明
     */
    SM("0598"),
    /**
     * 宁德
     */
    ND("0593"),
    /**
     * 龙岩
     */
    LY("0597"),
    /**
     * 厦门
     */
    XM("0592"),
    /**
     * 平潭
     */
    PINGT("05913571"),



    /**
     * 区域编号
     */

    FZS("3501"),
    /**
     * 泉州
     */
    QZS("3505"),
    /**
     * 漳州
     */
    ZZS("3506"),
    /**
     * 莆田
     */
    PTS("3503"),
    /**
     * 南平
     */
    NPS("3507"),
    /**
     * 三明
     */
    SMS("3504"),
    /**
     * 厦门市
     */
    XMS("3502"),
    /**
     * 宁德
     */
    NDS("3509"),
    /**
     * 龙岩
     */
    LYS("3508"),
    /**
     * 平潭综合实验区
     */
    PTZHSYQ("3571"),



    /**
     * 测试
     */
    CS("7"),
    /**
     * 山西省
     */
    SXS("1400"),
    /**
     * 高平市
     */
    GPS("1405");


    private String value;
    private AddressType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
