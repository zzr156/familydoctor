package com.ylz.packcommon.common.comEnum;

/**
 * 疾病标签
 * Created by zzl on 2018/7/25.
 */
public enum DiseaseType {


    /**
     * 结核病
     */
    JHB("208"),
    /**
     * 严重精神障碍
     */
    YZJSZA("207"),
    /**
     * 高血压
     */
    GXY("201"),
    /**
     * 糖尿病
     */
    TNB("202");

    private String value;
    private DiseaseType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
