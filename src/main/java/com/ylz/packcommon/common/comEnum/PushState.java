package com.ylz.packcommon.common.comEnum;

/**健康教育定期推送接收对象类型
 * Created by zzl on 2017/9/22.
 */
public enum PushState {
    //服务人群
    FWRQ("1"),
    //健康情况
    JKQK("2"),
    //疾病类型
    JBLB("3"),
    //指定人员
    ZDRY("4"),
    //全部人员
    QBRY("5");
    private String value;
    private PushState(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
