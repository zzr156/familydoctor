package com.ylz.bizDo.app.entity;

import org.apache.commons.lang.StringUtils;

/**
 * Created by asus on 2017/11/02.
 */
public class AppServeRoleEntity {
    private String serSettingId;
    private String serRoleSonId;
    private String serObjectTitle;
    private String serTitle;
    private String setNum;
    private String setSpace;
    private String setSpaceType;

    public String getSerSettingId() {
        return serSettingId;
    }

    public void setSerSettingId(String serSettingId) {
        this.serSettingId = serSettingId;
    }

    public String getSerRoleSonId() {
        return serRoleSonId;
    }

    public void setSerRoleSonId(String serRoleSonId) {
        this.serRoleSonId = serRoleSonId;
    }

    public String getSerObjectTitle() {
        return serObjectTitle;
    }

    public void setSerObjectTitle(String serObjectTitle) {
        this.serObjectTitle = serObjectTitle;
    }

    public String getSerTitle() {
        return serTitle;
    }

    public void setSerTitle(String serTitle) {
        this.serTitle = serTitle;
    }

    public String getSetNum() {
        return setNum;
    }

    public void setSetNum(String setNum) {
        String result = "0";
        if(StringUtils.isNotBlank(setNum)){
            result = setNum;
        }
        this.setNum = result;
    }

    public String getSetSpace() {
        return setSpace;
    }

    public void setSetSpace(String setSpace) {
        String result = "0";
        if(StringUtils.isNotBlank(setSpace)){
            result = setSpace;
        }
        this.setSpace = result;
    }

    public String getSetSpaceType() {
        return setSpaceType;
    }

    public void setSetSpaceType(String setSpaceType) {
        String result = "5";
        if(StringUtils.isNotBlank(setSpaceType)){
            result = setSpaceType;
        }
        this.setSpaceType = result;
    }
}
