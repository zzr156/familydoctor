package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/6/16.
 */
public class AppTeamMemberQvo extends CommConditionVo {
    private String appMemTeamId;//团队主键

    public String getAppMemTeamId() {
        return appMemTeamId;
    }

    public void setAppMemTeamId(String appMemTeamId) {
        this.appMemTeamId = appMemTeamId;
    }
}
