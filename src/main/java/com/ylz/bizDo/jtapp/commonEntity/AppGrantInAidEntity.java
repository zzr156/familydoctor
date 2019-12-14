package com.ylz.bizDo.jtapp.commonEntity;

/**
 * 财政补贴列表
 * Created by zzl on 2018/7/11.
 */
public class AppGrantInAidEntity {
    private String id;//财政补贴主键
//    private String econId;//经济类型主键
    private String econName;//经济类型名称
    private String govNames;//政府名称
    private String price;//费用

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEconName() {
        return econName;
    }

    public void setEconName(String econName) {
        this.econName = econName;
    }

    public String getGovNames() {
        return govNames;
    }

    public void setGovNames(String govNames) {
        this.govNames = govNames;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
