package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * 分组管理vo
 */
public class AppGroupVo extends CommConditionVo {
    private String groupName;//组名中文 服务人群
    private String groupCode;//组名英文 residentMange
    private String groupOption;//基础属性名称 普通人群
    private String groupValue;//基础属性值 1
    private String groupSort;//排序
    private String state;//启动或禁用

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupOption() {
        return groupOption;
    }

    public void setGroupOption(String groupOption) {
        this.groupOption = groupOption;
    }

    public String getGroupValue() {
        return groupValue;
    }

    public void setGroupValue(String groupValue) {
        this.groupValue = groupValue;
    }

    public String getGroupSort() {
        return groupSort;
    }

    public void setGroupSort(String groupSort) {
        this.groupSort = groupSort;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
