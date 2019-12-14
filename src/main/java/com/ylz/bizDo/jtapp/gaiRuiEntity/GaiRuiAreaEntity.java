package com.ylz.bizDo.jtapp.gaiRuiEntity;

/**
 * Created by zzl on 2019/3/25.
 */
public class GaiRuiAreaEntity {
    private String areaId;//	行政区划
    private String areaAllName;//		行政区划全称
    private String areaName;//		行政区划简称
    private String upId;//		上级行政区划

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaAllName() {
        return areaAllName;
    }

    public void setAreaAllName(String areaAllName) {
        this.areaAllName = areaAllName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUpId() {
        return upId;
    }

    public void setUpId(String upId) {
        this.upId = upId;
    }
}
