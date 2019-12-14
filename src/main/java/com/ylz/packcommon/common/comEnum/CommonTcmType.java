package com.ylz.packcommon.common.comEnum;

/**中医药保健指导
 * Created by zzl on 2017/8/30.
 */
public enum CommonTcmType  {
    YS("1"),//医生
    YY("2"),//医院
    XT("3");//系统
    private String value;
    private CommonTcmType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
