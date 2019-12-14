package com.ylz.bizDo.mangecount.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * 居民分析
 */
public class ResidentVo extends CommConditionVo{
    private String areaId;//区域主键
    private String hospId;//社区主键
    private String teamId;//团队主键
    private String drId;//医生主键
    private String yearStart;//开始年度
    private String yearEnd;//结束年度
    private String type;//类型
    private String hospLevel;//等级

    private String ptName;
    private String ptIdNo;
    private String ptType;
    private String arerCode;

    private String povertyState;//是否脱贫0否 1是
    private String persGroup;//服务人群
    private String jdAreaCode;//建档立卡街道行政区划
    private String jdSourceType;//建档配制（0查询医保数据  1查询民政数据）

    public String getHospLevel() {
        return hospLevel;
    }

    public void setHospLevel(String hospLevel) {
        this.hospLevel = hospLevel;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getYearStart() {
        return yearStart;
    }

    public void setYearStart(String yearStart) {
        this.yearStart = yearStart;
    }

    public String getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(String yearEnd) {
        this.yearEnd = yearEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPtName() {
        return ptName;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
    }

    public String getPtIdNo() {
        return ptIdNo;
    }

    public void setPtIdNo(String ptIdNo) {
        this.ptIdNo = ptIdNo;
    }

    public String getPtType() {
        return ptType;
    }

    public void setPtType(String ptType) {
        this.ptType = ptType;
    }

    public String getArerCode() {
        return arerCode;
    }

    public void setArerCode(String arerCode) {
        this.arerCode = arerCode;
    }

    public String getPovertyState() {
        return povertyState;
    }

    public void setPovertyState(String povertyState) {
        this.povertyState = povertyState;
    }

    public String getPersGroup() {
        return persGroup;
    }

    public void setPersGroup(String persGroup) {
        this.persGroup = persGroup;
    }

    public String getJdAreaCode() {
        return jdAreaCode;
    }

    public void setJdAreaCode(String jdAreaCode) {
        this.jdAreaCode = jdAreaCode;
    }

    public String getJdSourceType() {
        return jdSourceType;
    }

    public void setJdSourceType(String jdSourceType) {
        this.jdSourceType = jdSourceType;
    }
}
