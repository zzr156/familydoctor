package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by asus on 2017/7/6.
 */
public class AppManageCardQvo extends CommConditionVo {
    private String teamId;//团队主键
    private String id;//主键

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
