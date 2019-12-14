package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**收藏健康教育
 * Created by zzl on 2017/6/22.
 */
public class AppEnshrineHealthQvo extends CommConditionVo {
    private String userId;//用户id
    private String userName;//用户名称
    private String healthId;//健康教育id

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHealthId() {
        return healthId;
    }

    public void setHealthId(String healthId) {
        this.healthId = healthId;
    }
}
