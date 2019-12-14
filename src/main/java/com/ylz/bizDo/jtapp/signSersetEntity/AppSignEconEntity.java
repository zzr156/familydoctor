package com.ylz.bizDo.jtapp.signSersetEntity;

import java.util.List;

/**经济类型
 * Created by zzl on 2017/8/23.
 */
public class AppSignEconEntity {
    private String econId;//经济类型id
    private String econValue;//经济类型值
    private String econTitle;//经济类型名称
    private String econJcType;//是否是系统经济类型
    private String econTabState;//标记
    private List<AppSignGovEntity> govList;//补贴方式

    public String getEconId() {
        return econId;
    }

    public void setEconId(String econId) {
        this.econId = econId;
    }

    public String getEconValue() {
        return econValue;
    }

    public void setEconValue(String econValue) {
        this.econValue = econValue;
    }

    public String getEconTitle() {
        return econTitle;
    }

    public void setEconTitle(String econTitle) {
        this.econTitle = econTitle;
    }

    public String getEconJcType() {
        return econJcType;
    }

    public void setEconJcType(String econJcType) {
        this.econJcType = econJcType;
    }

    public String getEconTabState() {
        return econTabState;
    }

    public void setEconTabState(String econTabState) {
        this.econTabState = econTabState;
    }

    public List<AppSignGovEntity> getGovList() {
        return govList;
    }

    public void setGovList(List<AppSignGovEntity> govList) {
        this.govList = govList;
    }
}
