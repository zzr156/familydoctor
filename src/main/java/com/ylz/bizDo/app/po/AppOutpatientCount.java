package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by lenovo on 2017/12/27.
 * 门诊次数 医保支出 统计表
 */
@Entity
@Table(name = "APP_OUTPATIENT_COUNT")
public class  AppOutpatientCount extends BasePO {

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "OUTPATIENT_HOSP_ID",  length = 36)
    private String outpatientHospId;//机构id
    @Column(name = "OUTPATIENT_AREA_CODE" ,  length = 12)
    private String outpatientAreaCode;//机构编码
    @Column(name = "OUTPATIENT_TEAM_ID" ,  length = 36)
    private String outpatientTeamId;//团队id
    @Column(name = "OUTPATIENT_YEAR" ,  length = 10)
    private String outpatientYear;//年份
    @Column(name = "OUTPATIENT_MONTH" ,  length = 10)
    private String outpatientMonth;//月份
    @Column(name = "OUTPATIENT_YEAR_MONTH" ,  length = 10)
    private String outpatientYearMonth;//年月
    @Column(name = "OUTPATIENT_HOSPLEVEL_ONE" ,  length = 10)
    private String outpatientHosplevelOne;//一级医院
    @Column(name = "OUTPATIENT_HOSPLEVEL_TWO" ,  length = 10)
    private String outpatientHosplevelTwo;//二级医院
    @Column(name = "OUTPATIENT_HOSPLEVEL_THREE" ,  length = 10)
    private String outpatientHosplevelThree;//三级医院
    @Column(name = "OUTPATIENT_HOSPCOUNT" ,  length = 10)
    private String outpatientHospcount;//医院合计
    @Column(name = "OUTPATIENT_HOSP_PROPORTION" ,  length = 10)
    private String outpatientHospProportion;//基础比例
    @Column(name = "OUTPATIENT_LASTYEAR_COST" ,  length = 10)
    private String outpatientLastyearCost;//上年度支出
    @Column(name = "OUTPATIENT_THISYEAR_COST" ,  length = 10)
    private String outpatientThisyearCost;//本年度支出


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutpatientHospId() {
        return outpatientHospId;
    }

    public void setOutpatientHospId(String outpatientHospId) {
        this.outpatientHospId = outpatientHospId;
    }

    public String getOutpatientAreaCode() {
        return outpatientAreaCode;
    }

    public void setOutpatientAreaCode(String outpatientAreaCode) {
        this.outpatientAreaCode = outpatientAreaCode;
    }

    public String getOutpatientTeamId() {
        return outpatientTeamId;
    }

    public void setOutpatientTeamId(String outpatientTeamId) {
        this.outpatientTeamId = outpatientTeamId;
    }

    public String getOutpatientYear() {
        return outpatientYear;
    }

    public void setOutpatientYear(String outpatientYear) {
        this.outpatientYear = outpatientYear;
    }

    public String getOutpatientYearMonth() {
        return outpatientYearMonth;
    }

    public void setOutpatientYearMonth(String outpatientYearMonth) {
        this.outpatientYearMonth = outpatientYearMonth;
    }

    public String getOutpatientHosplevelOne() {
        return outpatientHosplevelOne;
    }

    public void setOutpatientHosplevelOne(String outpatientHosplevelOne) {
        this.outpatientHosplevelOne = outpatientHosplevelOne;
    }

    public String getOutpatientHosplevelTwo() {
        return outpatientHosplevelTwo;
    }

    public void setOutpatientHosplevelTwo(String outpatientHosplevelTwo) {
        this.outpatientHosplevelTwo = outpatientHosplevelTwo;
    }

    public String getOutpatientHosplevelThree() {
        return outpatientHosplevelThree;
    }

    public void setOutpatientHosplevelThree(String outpatientHosplevelThree) {
        this.outpatientHosplevelThree = outpatientHosplevelThree;
    }

    public String getOutpatientHospcount() {
        return outpatientHospcount;
    }

    public void setOutpatientHospcount(String outpatientHospcount) {
        this.outpatientHospcount = outpatientHospcount;
    }

    public String getOutpatientHospProportion() {
        return outpatientHospProportion;
    }

    public void setOutpatientHospProportion(String outpatientHospProportion) {
        this.outpatientHospProportion = outpatientHospProportion;
    }

    public String getOutpatientLastyearCost() {
        return outpatientLastyearCost;
    }

    public void setOutpatientLastyearCost(String outpatientLastyearCost) {
        this.outpatientLastyearCost = outpatientLastyearCost;
    }

    public String getOutpatientThisyearCost() {
        return outpatientThisyearCost;
    }

    public void setOutpatientThisyearCost(String outpatientThisyearCost) {
        this.outpatientThisyearCost = outpatientThisyearCost;
    }

    public String getOutpatientMonth() {
        return outpatientMonth;
    }

    public void setOutpatientMonth(String outpatientMonth) {
        this.outpatientMonth = outpatientMonth;
    }
}
