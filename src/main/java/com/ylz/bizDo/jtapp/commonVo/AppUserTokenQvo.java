package com.ylz.bizDo.jtapp.commonVo;

/**
 * Created by asus on 2017-7-13.
 */
public class AppUserTokenQvo {
    private String commonId;
    private String commonUserId;
    private String type;//1居民,2医生,3pos

    public String getCommonId() {
        return commonId;
    }

    public void setCommonId(String commonId) {
        this.commonId = commonId;
    }

    public String getCommonUserId() {
        return commonUserId;
    }

    public void setCommonUserId(String commonUserId) {
        this.commonUserId = commonUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
