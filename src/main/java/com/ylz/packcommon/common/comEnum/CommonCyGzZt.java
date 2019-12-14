package com.ylz.packcommon.common.comEnum;

/**
 * 餐饮关注状态
 */
public enum CommonCyGzZt {

    /**
     * 取消
     */
    QUXIAO("0"),

    /**
     * 添加
     */
    TIANJIA("1");
    private String value;
    private CommonCyGzZt(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
