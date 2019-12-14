package com.ylz.bizDo.web.vo;

import java.util.List;

/**
 * Created by zzl on 2018/3/13.
 */
public class WebEconGovVo {
    private String econGovId;//经济与补贴id
    private String econId;//经济类型id
    private String econValue;//经济类型值
    private String econName;//经济类型名称
    //政府补贴信息
    private List<WebGovVo> govVos;

    public String getEconGovId() {
        return econGovId;
    }

    public void setEconGovId(String econGovId) {
        this.econGovId = econGovId;
    }

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

    public String getEconName() {
        return econName;
    }

    public void setEconName(String econName) {
        this.econName = econName;
    }

    public List<WebGovVo> getGovVos() {
        return govVos;
    }

    public void setGovVos(List<WebGovVo> govVos) {
        this.govVos = govVos;
    }
}
