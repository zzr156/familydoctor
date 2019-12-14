package com.ylz.packcommon.common.comEnum;

/**
 * Created by asus on 2017/7/3.
 */
public enum DrPatientType {


    /**
     *  1 患者
     */
    PATIENT("1"),
    /**
     *  2 医生
     */
    DR("2");
    private String value;
    private DrPatientType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
