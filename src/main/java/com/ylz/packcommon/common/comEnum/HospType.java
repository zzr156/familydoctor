package com.ylz.packcommon.common.comEnum;

/**
 * Created by zzl on 2018/3/19.
 */
public enum HospType {
    /**
     * 综合医院
     */
    ZHYY("0"),
    /**
     * 专科医院
     */
    ZKYY("1"),
    /**
     * 中医医院
     */
    ZYYY("2"),
    /**
     * 中西医结合医院
     */
    ZXYJHYY("3"),
    /**
     * 民族医医院
     */
    MZYYY("4"),
    /**
     * 康复医院
     */
    KFYY("5"),
    /**
     * 妇幼保健院
     */
    FYBJY("6"),
    /**
     * 社区卫生服务中心（站）
     */
    SQWSFWZX("7"),
    /**
     * 中心卫生院
     */
    ZXWSY("8"),
    /**
     * 乡镇卫生院
     */
    XZWSY("9");
    private String value;
    private HospType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
