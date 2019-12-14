package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/7/16.
 */
public class AppDrTargetQvo extends CommConditionVo {
    private String id;//主键id
    private String areaName;//地区名称
    private String year;//年份
    private String upAreaCode;//区域上级编号
    private String areaCode;//区域编号
    private String areaPopulation;//人口数
    private String areaRate;//比率
    private String areaFocus;//重点人群数
    private String areaFocusRate;//重点人群数比率
    private String signTop;//签约上限人数
    private String signWay;//签约上限方式
    private String disSignTop;//慢性病患者签约上限人数
    private String disSignWay;//上限方式
    private String areaEconomic;//人口经济性质人数
    private String areaEconomicRate;//人口经济性质比率
    //web改造
    private String areaSignTop;//签约上限人数
    private String areaSignWay;//签约上限方式
    private String areaDisSignTop;//慢性病患者签约上限人数
    private String areaDisSignWay;//上限方式

    private String areaEconomicJklm; // 人口经济性质-建卡立名管理数
    private String areaEconomicDbh;  // 人口经济性质-低保户管理数
    private String areaEconomicTkh;  // 人口经济性质-特困户管理数
    private String areaEconomicJsjt; // 人口经济性质-计生家庭人口管理数

    private String orgid; // 机构id

    public String getAreaSignTop() {
        return areaSignTop;
    }

    public void setAreaSignTop(String areaSignTop) {
        this.signTop=areaSignTop;
        this.areaSignTop = areaSignTop;
    }

    public String getAreaSignWay() {
        return areaSignWay;
    }

    public void setAreaSignWay(String areaSignWay) {
        this.signWay=areaSignWay;
        this.areaSignWay = areaSignWay;
    }

    public String getAreaDisSignTop() {
        return areaDisSignTop;
    }

    public void setAreaDisSignTop(String areaDisSignTop) {
        this.disSignTop=areaDisSignTop;
        this.areaDisSignTop = areaDisSignTop;
    }

    public String getAreaDisSignWay() {
        return areaDisSignWay;
    }

    public void setAreaDisSignWay(String areaDisSignWay) {
        this.disSignWay=areaDisSignWay;
        this.areaDisSignWay = areaDisSignWay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAreaPopulation() {
        return areaPopulation;
    }

    public void setAreaPopulation(String areaPopulation) {
        this.areaPopulation = areaPopulation;
    }

    public String getAreaRate() {
        return areaRate;
    }

    public void setAreaRate(String areaRate) {
        this.areaRate = areaRate;
    }

    public String getUpAreaCode() {
        return upAreaCode;
    }

    public void setUpAreaCode(String upAreaCode) {
        this.upAreaCode = upAreaCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaFocus() {
        return areaFocus;
    }

    public void setAreaFocus(String areaFocus) {
        this.areaFocus = areaFocus;
    }

    public String getAreaFocusRate() {
        return areaFocusRate;
    }

    public void setAreaFocusRate(String areaFocusRate) {
        this.areaFocusRate = areaFocusRate;
    }

    public String getSignTop() {
        return signTop;
    }

    public void setSignTop(String signTop) {
        this.signTop = signTop;
    }

    public String getSignWay() {
        return signWay;
    }

    public void setSignWay(String signWay) {
        this.signWay = signWay;
    }

    public String getDisSignTop() {
        return disSignTop;
    }

    public void setDisSignTop(String disSignTop) {
        this.disSignTop = disSignTop;
    }

    public String getDisSignWay() {
        return disSignWay;
    }

    public void setDisSignWay(String disSignWay) {
        this.disSignWay = disSignWay;
    }

    public String getAreaEconomic() {
        return areaEconomic;
    }

    public void setAreaEconomic(String areaEconomic) {
        this.areaEconomic = areaEconomic;
    }

    public String getAreaEconomicRate() {
        return areaEconomicRate;
    }

    public void setAreaEconomicRate(String areaEconomicRate) {
        this.areaEconomicRate = areaEconomicRate;
    }

    public String getAreaEconomicJklm() {
        return areaEconomicJklm;
    }

    public void setAreaEconomicJklm(String areaEconomicJklm) {
        this.areaEconomicJklm = areaEconomicJklm;
    }

    public String getAreaEconomicDbh() {
        return areaEconomicDbh;
    }

    public void setAreaEconomicDbh(String areaEconomicDbh) {
        this.areaEconomicDbh = areaEconomicDbh;
    }

    public String getAreaEconomicTkh() {
        return areaEconomicTkh;
    }

    public void setAreaEconomicTkh(String areaEconomicTkh) {
        this.areaEconomicTkh = areaEconomicTkh;
    }

    public String getAreaEconomicJsjt() {
        return areaEconomicJsjt;
    }

    public void setAreaEconomicJsjt(String areaEconomicJsjt) {
        this.areaEconomicJsjt = areaEconomicJsjt;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }
}
