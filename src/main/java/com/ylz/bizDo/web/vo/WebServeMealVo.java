package com.ylz.bizDo.web.vo;

import java.util.List;

/**
 * 服务套餐vo
 * Created by zzl on 2018/3/13.
 */
public class WebServeMealVo {
    private String sersmId;//套餐id
    private String sersmValue;//服务包编号
    private String sersmName;//服务包名称
    private String sersmYxTimeType;//服务包协议有效方式 1签约开始，2扣费开始，3长期有效，4按需
    private String sersmStartTime;//服务包协议有效开始时间 sersmYxTimeType为4时有值
    private String sersmEndTime;//服务包协议有效结束时间 sersmYxTimeType为4时有值
    private String sersmBgDr;//有效期间是否可变更医生
    private String sersmJcState;//是否是基础服务包 0否 1是

    private String deptId;//单位id
    private String areaCodeCity;//区域编号
    private String drId;//医生id
    private String strAre;

    //组合内容
    private List<WebServeGroupVo> groupVos;
    //经济与政府补贴
    private List<WebEconGovVo> econGovVos;

    public String getSersmId() {
        return sersmId;
    }

    public void setSersmId(String sersmId) {
        this.sersmId = sersmId;
    }

    public String getSersmValue() {
        return sersmValue;
    }

    public void setSersmValue(String sersmValue) {
        this.sersmValue = sersmValue;
    }

    public String getSersmName() {
        return sersmName;
    }

    public void setSersmName(String sersmName) {
        this.sersmName = sersmName;
    }

    public String getSersmYxTimeType() {
        return sersmYxTimeType;
    }

    public void setSersmYxTimeType(String sersmYxTimeType) {
        this.sersmYxTimeType = sersmYxTimeType;
    }

    public String getSersmStartTime() {
        return sersmStartTime;
    }

    public void setSersmStartTime(String sersmStartTime) {
        this.sersmStartTime = sersmStartTime;
    }

    public String getSersmEndTime() {
        return sersmEndTime;
    }

    public void setSersmEndTime(String sersmEndTime) {
        this.sersmEndTime = sersmEndTime;
    }

    public String getSersmBgDr() {
        return sersmBgDr;
    }

    public void setSersmBgDr(String sersmBgDr) {
        this.sersmBgDr = sersmBgDr;
    }

    public String getSersmJcState() {
        return sersmJcState;
    }

    public void setSersmJcState(String sersmJcState) {
        this.sersmJcState = sersmJcState;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getAreaCodeCity() {
        return areaCodeCity;
    }

    public void setAreaCodeCity(String areaCodeCity) {
        this.areaCodeCity = areaCodeCity;
    }

    public List<WebServeGroupVo> getGroupVos() {
        return groupVos;
    }

    public void setGroupVos(List<WebServeGroupVo> groupVos) {
        this.groupVos = groupVos;
    }

    public List<WebEconGovVo> getEconGovVos() {
        return econGovVos;
    }

    public void setEconGovVos(List<WebEconGovVo> econGovVos) {
        this.econGovVos = econGovVos;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getStrAre() {
        return strAre;
    }

    public void setStrAre(String strAre) {
        this.strAre = strAre;
    }
}
