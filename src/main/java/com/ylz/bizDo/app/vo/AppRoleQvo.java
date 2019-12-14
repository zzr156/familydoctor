package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * 手机权限
 */
public class AppRoleQvo extends CommConditionVo {
    private String appRoleName;
    private String appRoleState;

    public String getAppRoleName() {
        return appRoleName;
    }

    public void setAppRoleName(String appRoleName) {
        this.appRoleName = appRoleName;
    }

    public String getAppRoleState() {
        return appRoleState;
    }

    public void setAppRoleState(String appRoleState) {
        this.appRoleState = appRoleState;
    }
}
