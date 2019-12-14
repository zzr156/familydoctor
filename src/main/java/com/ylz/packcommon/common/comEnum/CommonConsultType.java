package com.ylz.packcommon.common.comEnum;

/**咨询
 * Created by zzl on 2017/6/26.
 */
public enum CommonConsultType {
    /**
     * 家庭医生
     */
    JTYS("0"),
    /**
     * 公共咨询
     */
    GGZX("1"),
    /**
     * 名医咨询
     */
    MYZX("2");
    private String value;
    CommonConsultType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
