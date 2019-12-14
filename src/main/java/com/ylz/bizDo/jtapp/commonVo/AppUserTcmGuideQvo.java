package com.ylz.bizDo.jtapp.commonVo;

/**查询中医药保健指导记录
 * Created by zzl on 2017/8/30.
 */
public class AppUserTcmGuideQvo {
    private String type;//1患者 2医生
    private String userId;//用户id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
