package com.ylz.bizDo.cd.vo;

import java.util.Calendar;

/**
 * 首页表格统计（莆田）
 * cxw
 * Created by Administrator on 2018/1/25.
 */
public class CdAddressPeopleVo {
    private String areaEconomicJklm; // 人口经济性质-建卡立名管理数
    private String areaEconomicDbh;  // 人口经济性质-低保户管理数
    private String areaEconomicTkh;  // 人口经济性质-特困户管理数
    private String areaEconomicJsjt; // 人口经济性质-计生家庭人口管理数

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
}
