package com.ylz.bizDo.jtapp.commonVo;

/**
 * Created by asus on 2017/7/5.
 */
public class AppEaseVo {
    private String easeId;//环信主键
    private String type;//类型 1 用户 2 患者

    public String getEaseId() {
        return easeId;
    }

    public void setEaseId(String easeId) {
        this.easeId = easeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
