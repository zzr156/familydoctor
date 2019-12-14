package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 福建省建档立卡农村贫困人口家庭医生签约服务工作情况市级统计表
 * Created by zzl on 2018/7/28.
 */
@Entity
@Table(name = "APP_MANAGE_ARCHIVING_PEOPLE")
public class AppManageArchivingPeople extends BasePO{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    private String id;
    @Column(name = "CITY_CODE",length = 50)
    private String cityCode;//城市编码
    @Column(name = "AREA_CODE",length = 50)
    private String areaCode;//区县编码
    @Column(name = "STREET_CODE",length = 50)
    private String streetCode;//街道编码
    @Column(name = "HOSP_ID",length = 36)
    private String hospId;//医院id
    @Column(name = "TOTAL_ARCHIVING_CONT",length = 20)
    private String totalArchivingCount;//建档立卡人员数
    @Column(name = "TOTAL_SIGN_COUNT",length = 20)
    private String totalSignCount;//累计已签约人数
    @Column(name = "DIE_COUNT",length = 20)
    private String dieCount;//未签约死亡数（死亡）
    @Column(name = "MISS_COUNT",length = 20)
    private String missCount;//失联失踪人数
    @Column(name = "MOVE_OUT_COUNT",length = 20)
    private String moveOutCount;//迁出人数（迁出）
    @Column(name = "LONG_TIME_OUT_COUNT",length = 20)
    private String longTimeOutCount;//长期外出人数（长期外出）
    @Column(name = "REFUSE_SIGN_COUNT",length = 20)
    private String refuseSignCount;//拒签人数（拒签）
    @Column(name = "BE_EXECUTED_COUNT",length = 20)
    private String beExecotedCount;//服刑人数（服刑）
    @Column(name = "NAME_REPEAT_COUNT",length = 20)
    private String nameRepeatCount;//名单重复人数（信息重复）
    @Column(name = "MENTAL_PATIENT_COUNT",length = 20)
    private String mentalPatientCount;//精神病人住康复医院，家属外出人数（精神病院强制住院）
    @Column(name = "PERFORM_MILITARY_SERVICE",length = 20)
    private String performMilitaryService;//服兵役人数（服兵役）
    @Column(name = "FOREIGN_NATIONALITY",length = 20)
    private String foreignNationality;//外籍人数（外籍）
    @Column(name = "MARRY_OUT_COUNT",length = 20)
    private String marryOutCount;//外嫁人数（未入户）
    @Column(name = "NEW_PERSONNEL_COUNT",length = 20)
    private String newPersonnelCount;//新增人员（新增人员）
    @Column(name = "NO_CONNECTION_COUNT",length = 20)
    private String noConnectionCount;//联系不上人数（联系不上）
    @Column(name = "IDNO_DISCREPANCY_COUNT",length = 20)
    private String idnoDiscrepancyCount;//身份名字不符人数（身份证信息错误）
    @Column(name = "NOT_SIGN_GO_OUT",length = 20)
    private String notSignGoOut;//暂时外出无法签约（暂时未签约）
    @Column(name = "FIELD_ARCHIVING_COUNT",length = 20)
    private String fieldArchivingCount;//外地建档人数(外地建档)
    @Column(name = "QUIT_COUNT",length = 20)
    private String quitCount;//退出人数（异地签约）
    @Column(name = "NOT_CARD_COUNT",length = 20)
    private String notCardCount;//无社保卡人数（无有效证件）
    @Column(name = "GO_ABROAD_COUNT",length = 20)
    private String goAbroadCount;//出国人数
    @Column(name = "OTHER_COUNT",length = 20)
    private String otherCount;//其它人数
    @Column(name = "UNFILLED_COUNT",length = 20)
    private String unfilledCount;//未填写人数
    @Column(name = "MANAGE_YEAR",length = 10)
    private String manageYear;//年
    @Column(name = "MANAGE_MONTH",length = 20)
    private String manageMonth;//月
    @Column(name = "MANAGE_YEAR_MONTH",length = 20)
    private String manageYearMonth;//年月
    @Column(name = "MANAGE_TOTAL",length = 20)
    private String manageTotal;//合计
    @Column(name = "SIGN_LOW_INSURED_COUNT",length = 20)
    private String signLowInsuredCount;//已签约的低保户人数
    @Column(name = "ADDR_HOSP_ID",length = 36)
    private String addrHospId;//归管单位主键

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getDieCount() {
        return dieCount;
    }

    public void setDieCount(String dieCount) {
        this.dieCount = dieCount;
    }

    public String getMissCount() {
        return missCount;
    }

    public void setMissCount(String missCount) {
        this.missCount = missCount;
    }

    public String getMoveOutCount() {
        return moveOutCount;
    }

    public void setMoveOutCount(String moveOutCount) {
        this.moveOutCount = moveOutCount;
    }

    public String getLongTimeOutCount() {
        return longTimeOutCount;
    }

    public void setLongTimeOutCount(String longTimeOutCount) {
        this.longTimeOutCount = longTimeOutCount;
    }

    public String getRefuseSignCount() {
        return refuseSignCount;
    }

    public void setRefuseSignCount(String refuseSignCount) {
        this.refuseSignCount = refuseSignCount;
    }

    public String getBeExecotedCount() {
        return beExecotedCount;
    }

    public void setBeExecotedCount(String beExecotedCount) {
        this.beExecotedCount = beExecotedCount;
    }

    public String getNameRepeatCount() {
        return nameRepeatCount;
    }

    public void setNameRepeatCount(String nameRepeatCount) {
        this.nameRepeatCount = nameRepeatCount;
    }

    public String getMentalPatientCount() {
        return mentalPatientCount;
    }

    public void setMentalPatientCount(String mentalPatientCount) {
        this.mentalPatientCount = mentalPatientCount;
    }

    public String getPerformMilitaryService() {
        return performMilitaryService;
    }

    public void setPerformMilitaryService(String performMilitaryService) {
        this.performMilitaryService = performMilitaryService;
    }

    public String getForeignNationality() {
        return foreignNationality;
    }

    public void setForeignNationality(String foreignNationality) {
        this.foreignNationality = foreignNationality;
    }

    public String getMarryOutCount() {
        return marryOutCount;
    }

    public void setMarryOutCount(String marryOutCount) {
        this.marryOutCount = marryOutCount;
    }

    public String getNewPersonnelCount() {
        return newPersonnelCount;
    }

    public void setNewPersonnelCount(String newPersonnelCount) {
        this.newPersonnelCount = newPersonnelCount;
    }

    public String getNoConnectionCount() {
        return noConnectionCount;
    }

    public void setNoConnectionCount(String noConnectionCount) {
        this.noConnectionCount = noConnectionCount;
    }

    public String getIdnoDiscrepancyCount() {
        return idnoDiscrepancyCount;
    }

    public void setIdnoDiscrepancyCount(String idnoDiscrepancyCount) {
        this.idnoDiscrepancyCount = idnoDiscrepancyCount;
    }

    public String getNotSignGoOut() {
        return notSignGoOut;
    }

    public void setNotSignGoOut(String notSignGoOut) {
        this.notSignGoOut = notSignGoOut;
    }

    public String getFieldArchivingCount() {
        return fieldArchivingCount;
    }

    public void setFieldArchivingCount(String fieldArchivingCount) {
        this.fieldArchivingCount = fieldArchivingCount;
    }

    public String getQuitCount() {
        return quitCount;
    }

    public void setQuitCount(String quitCount) {
        this.quitCount = quitCount;
    }

    public String getNotCardCount() {
        return notCardCount;
    }

    public void setNotCardCount(String notCardCount) {
        this.notCardCount = notCardCount;
    }

    public String getGoAbroadCount() {
        return goAbroadCount;
    }

    public void setGoAbroadCount(String goAbroadCount) {
        this.goAbroadCount = goAbroadCount;
    }

    public String getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(String otherCount) {
        this.otherCount = otherCount;
    }

    public String getUnfilledCount() {
        return unfilledCount;
    }

    public void setUnfilledCount(String unfilledCount) {
        this.unfilledCount = unfilledCount;
    }

    public String getTotalArchivingCount() {
        return totalArchivingCount;
    }

    public void setTotalArchivingCount(String totalArchivingCount) {
        this.totalArchivingCount = totalArchivingCount;
    }

    public String getTotalSignCount() {
        return totalSignCount;
    }

    public void setTotalSignCount(String totalSignCount) {
        this.totalSignCount = totalSignCount;
    }

    public String getManageYear() {
        return manageYear;
    }

    public void setManageYear(String manageYear) {
        this.manageYear = manageYear;
    }

    public String getManageMonth() {
        return manageMonth;
    }

    public void setManageMonth(String manageMonth) {
        this.manageMonth = manageMonth;
    }

    public String getManageYearMonth() {
        return manageYearMonth;
    }

    public void setManageYearMonth(String manageYearMonth) {
        this.manageYearMonth = manageYearMonth;
    }

    public String getManageTotal() {
        return manageTotal;
    }

    public void setManageTotal(String manageTotal) {
        this.manageTotal = manageTotal;
    }

    public String getSignLowInsuredCount() {
        return signLowInsuredCount;
    }

    public void setSignLowInsuredCount(String signLowInsuredCount) {
        this.signLowInsuredCount = signLowInsuredCount;
    }

    public String getAddrHospId() {
        return addrHospId;
    }

    public void setAddrHospId(String addrHospId) {
        this.addrHospId = addrHospId;
    }
}
