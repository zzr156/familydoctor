package com.ylz.bizDo.web.vo;

/**
 * Created by zzl on 2018/3/13.
 */
public class WebGovVo {
    private String govId;//政府补贴类型id
    private String govValue;//政府补贴类型编号
    private String govName;//政府补贴类型名称
    private String govMoney;//政府补贴类型价格

    public String getGovId() {
        return govId;
    }

    public void setGovId(String govId) {
        this.govId = govId;
    }

    public String getGovValue() {
        return govValue;
    }

    public void setGovValue(String govValue) {
        this.govValue = govValue;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getGovMoney() {
        return govMoney;
    }

    public void setGovMoney(String govMoney) {
        this.govMoney = govMoney;
    }
}
