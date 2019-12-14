package com.ylz.bizDo.jtapp.drEntity;

/**
 * Created by zzl on 2017/7/16.
 */
public class AppDrTargetEntity  {
    private String id;//主键id
    private String areaSname;//区域简称
    private String areaCode;//地区编号
    private String areaPopulation;//总人数
    private String areaTarget;//目标数
    private String upAreaPopulation;//参考总人数
    private String areaFocus;//重点人群数
    private String upAreaFocus;//重点人群数参考值
    private String areaFocusTarget;//重点人群目标数
    private String areaYear;//年份
    private String areaRate;//目标比率
    private String areaFocusRate;//重点比率
    private String time;//创建时间
    private String areaName;//区域全称
    private String signTop;//签约上限人数
    private String signWay;//签约上限方式 0医生 1团队
    private String disSignTop;//慢性病患者签约上限人数
    private String disSignWay;//慢性病患者签约上限方式 0医生 1团队
    private String state;//状态
    private String economicTarget;//人口经济性质目标数
    private String upEconomic;//人口经济性质参考数
    private String economic;//人口经济性质数
    private String economicRate;//口经济性质签约率


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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaPopulation() {
        return areaPopulation;
    }

    public void setAreaPopulation(String areaPopulation) {
        this.areaPopulation = areaPopulation;
    }

    public String getAreaTarget() {
        return areaTarget;
    }

    public void setAreaTarget(String areaTarget) {
        this.areaTarget = areaTarget;
    }

    public String getAreaYear() {
        return areaYear;
    }

    public void setAreaYear(String areaYear) {
        this.areaYear = areaYear;
    }

    public String getAreaRate() {
        return areaRate;
    }

    public void setAreaRate(String areaRate) {
        this.areaRate = areaRate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAreaSname() {
        return areaSname;
    }

    public void setAreaSname(String areaSname) {
        this.areaSname = areaSname;
    }

    public String getUpAreaPopulation() {
        return upAreaPopulation;
    }

    public void setUpAreaPopulation(String upAreaPopulation) {
        this.upAreaPopulation = upAreaPopulation;
    }

    public String getAreaFocus() {
        return areaFocus;
    }

    public void setAreaFocus(String areaFocus) {
        this.areaFocus = areaFocus;
    }

    public String getUpAreaFocus() {
        return upAreaFocus;
    }

    public void setUpAreaFocus(String upAreaFocus) {
        this.upAreaFocus = upAreaFocus;
    }

    public String getAreaFocusTarget() {
        return areaFocusTarget;
    }

    public void setAreaFocusTarget(String areaFocusTarget) {
        this.areaFocusTarget = areaFocusTarget;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEconomicTarget() {
        return economicTarget;
    }

    public void setEconomicTarget(String economicTarget) {
        this.economicTarget = economicTarget;
    }

    public String getUpEconomic() {
        return upEconomic;
    }

    public void setUpEconomic(String upEconomic) {
        this.upEconomic = upEconomic;
    }

    public String getEconomic() {
        return economic;
    }

    public void setEconomic(String economic) {
        this.economic = economic;
    }

    public String getEconomicRate() {
        return economicRate;
    }

    public void setEconomicRate(String economicRate) {
        this.economicRate = economicRate;
    }
}
