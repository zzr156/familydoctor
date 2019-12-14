package com.ylz.bizDo.register.entity;

import java.util.List;

public class RegisterGovTileEntity {
    private String govTitle;//标题
    private String govMoney;//钱
    private String govHcProjectId;//医保项目ID

    public String getGovTitle() {
        return govTitle;
    }

    public void setGovTitle(String govTitle) {
        this.govTitle = govTitle;
    }

    public String getGovMoney() {
        return govMoney;
    }

    public void setGovMoney(String govMoney) {
        this.govMoney = govMoney;
    }

    public String getGovHcProjectId() {
        return govHcProjectId;
    }

    public void setGovHcProjectId(String govHcProjectId) {
        this.govHcProjectId = govHcProjectId;
    }
}
