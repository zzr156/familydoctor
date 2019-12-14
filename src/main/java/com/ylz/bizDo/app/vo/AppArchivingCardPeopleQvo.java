package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2018/7/17.
 */
public class AppArchivingCardPeopleQvo extends CommConditionVo {
    private String id;//主键
    private String patientCity;//市
    private String patientArea;//县
    private String patientStreet;//乡镇
    private String patientNeighborhoodCommittee;//村
    private String jdState;//是否建档(0否 1是)
    private String signState;//是否签约
    private String areaCode;//区域编号
    private String role;//权限
    private String povertyState;//是否脱贫
    private String orgName;
    private String notConfirm;//未确认
    private String drId;//医生id
    private String teamIds;//团队id

    private String addrRuralCode;
    private String addrRuralName;
    private String addrVillageCode;
    private String addrVillageName;

    private String lowInsured;//低保户（1是 0否）
    private String poorHouseholds;//特困户（1是 0否）
    private String isNotPoverty;//是否脱贫（1是 0否）
    private String notSignReason;//未签约原因
    private String patientTel;//联系电话

    private String removalState;//去重
    private String delReason;//删除原因

    private String jdgfState;//是否建档规范（删除状态是0的是）

    private String revokeState;//撤销类型
    private String revokeDate;//撤销时间
    private String revokeReason;//撤销原因

    private String otherReason;//未签其他原因

    private String[] villageState;//查询居委会异常数据
    private String[] cancelState;//是否查看注销数据
    private String[] idnoState;//身份证异常
    private String patientIdno;//身份证
    private String[] toDayState;//查询当天签约数
    private String jdSourceType;//各地市建档来源配制（0医保 1民政）
    private String provincialInsurance;//对象类型 (1 建档立卡贫困人口,2省定扶贫标准下的低保户对象)
    private String remarks;//备注
    private String patientName;//姓名

    private String[] isHaveState;//是否添加核查表
    private String checkYear;//核查年份

    private String checkStartDate;//核查开始日期
    private String checkEndDate;//核查结束日期



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientCity() {
        return patientCity;
    }

    public void setPatientCity(String patientCity) {
        this.patientCity = patientCity;
    }

    public String getPatientArea() {
        return patientArea;
    }

    public void setPatientArea(String patientArea) {
        this.patientArea = patientArea;
    }

    public String getPatientStreet() {
        return patientStreet;
    }

    public void setPatientStreet(String patientStreet) {
        this.patientStreet = patientStreet;
    }

    public String getPatientNeighborhoodCommittee() {
        return patientNeighborhoodCommittee;
    }

    public void setPatientNeighborhoodCommittee(String patientNeighborhoodCommittee) {
        this.patientNeighborhoodCommittee = patientNeighborhoodCommittee;
    }

    public String getJdState() {
        return jdState;
    }

    public void setJdState(String jdState) {
        this.jdState = jdState;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPovertyState() {
        return povertyState;
    }

    public void setPovertyState(String povertyState) {
        this.povertyState = povertyState;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getNotConfirm() {
        return notConfirm;
    }

    public void setNotConfirm(String notConfirm) {
        this.notConfirm = notConfirm;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(String teamIds) {
        this.teamIds = teamIds;
    }

    public String getAddrRuralCode() {
        return addrRuralCode;
    }

    public void setAddrRuralCode(String addrRuralCode) {
        this.addrRuralCode = addrRuralCode;
    }

    public String getAddrRuralName() {
        return addrRuralName;
    }

    public void setAddrRuralName(String addrRuralName) {
        this.addrRuralName = addrRuralName;
    }

    public String getAddrVillageCode() {
        return addrVillageCode;
    }

    public void setAddrVillageCode(String addrVillageCode) {
        this.addrVillageCode = addrVillageCode;
    }

    public String getAddrVillageName() {
        return addrVillageName;
    }

    public void setAddrVillageName(String addrVillageName) {
        this.addrVillageName = addrVillageName;
    }

    public String getLowInsured() {
        return lowInsured;
    }

    public void setLowInsured(String lowInsured) {
        this.lowInsured = lowInsured;
    }

    public String getPoorHouseholds() {
        return poorHouseholds;
    }

    public void setPoorHouseholds(String poorHouseholds) {
        this.poorHouseholds = poorHouseholds;
    }

    public String getIsNotPoverty() {
        return isNotPoverty;
    }

    public void setIsNotPoverty(String isNotPoverty) {
        this.isNotPoverty = isNotPoverty;
    }

    public String getNotSignReason() {
        return notSignReason;
    }

    public void setNotSignReason(String notSignReason) {
        this.notSignReason = notSignReason;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getRemovalState() {
        return removalState;
    }

    public void setRemovalState(String removalState) {
        this.removalState = removalState;
    }

    public String getDelReason() {
        return delReason;
    }

    public void setDelReason(String delReason) {
        this.delReason = delReason;
    }

    public String getJdgfState() {
        return jdgfState;
    }

    public void setJdgfState(String jdgfState) {
        this.jdgfState = jdgfState;
    }

    public String getRevokeState() {
        return revokeState;
    }

    public void setRevokeState(String revokeState) {
        this.revokeState = revokeState;
    }

    public String getRevokeDate() {
        return revokeDate;
    }

    public void setRevokeDate(String revokeDate) {
        this.revokeDate = revokeDate;
    }

    public String getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public String[] getVillageState() {
        return villageState;
    }

    public void setVillageState(String[] villageState) {
        this.villageState = villageState;
    }

    public String[] getCancelState() {
        return cancelState;
    }

    public void setCancelState(String[] cancelState) {
        this.cancelState = cancelState;
    }

    public String[] getIdnoState() {
        return idnoState;
    }

    public void setIdnoState(String[] idnoState) {
        this.idnoState = idnoState;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String[] getToDayState() {
        return toDayState;
    }

    public void setToDayState(String[] toDayState) {
        this.toDayState = toDayState;
    }

    public String getJdSourceType() {
        return jdSourceType;
    }

    public void setJdSourceType(String jdSourceType) {
        this.jdSourceType = jdSourceType;
    }

    public String getProvincialInsurance() {
        return provincialInsurance;
    }

    public void setProvincialInsurance(String provincialInsurance) {
        this.provincialInsurance = provincialInsurance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String[] getIsHaveState() {
        return isHaveState;
    }

    public void setIsHaveState(String[] isHaveState) {
        this.isHaveState = isHaveState;
    }

    public String getCheckYear() {
        return checkYear;
    }

    public void setCheckYear(String checkYear) {
        this.checkYear = checkYear;
    }

    public String getCheckStartDate() {
        return checkStartDate;
    }

    public void setCheckStartDate(String checkStartDate) {
        this.checkStartDate = checkStartDate;
    }

    public String getCheckEndDate() {
        return checkEndDate;
    }

    public void setCheckEndDate(String checkEndDate) {
        this.checkEndDate = checkEndDate;
    }
}
