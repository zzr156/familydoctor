package com.ylz.bizDo.jtapp.patientVo;

import com.ylz.packcommon.common.CommConditionVo;

/**个人健康查询条件
 * Created by zzl on 2017/7/10.
 */
public class AppPatientHealthQvo extends CommConditionVo {
    private String id;//主键
    private String userId;//用户id
    private String type;//类型
    private String healthId;//健康id
    private String userName;//用户姓名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHealthId() {
        return healthId;
    }

    public void setHealthId(String healthId) {
        this.healthId = healthId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
