package com.ylz.bizDo.app.entity;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by lenovo on 2017/12/27.
 * 门诊次数 医保支出 统计表
 */

public class AppOutpatientCountEntity  {

    private String id;
    private String outpatientHospId;//机构id
    private String outpatientAreaCode;//机构编码
    private String outpatientTeamId;//团队id
    private String outpatientYear;//年份
    private String outpatientMonth;//月份
    private String outpatientYearMonth;//年月
    private String outpatientHosplevelOne;//一级医院
    private String outpatientHosplevelTwo;//二级医院
    private String outpatientHosplevelThree;//三级医院
    private String outpatientHospcount;//医院合计
    private String outpatientHospProportion;//基础比例
    private String outpatientLastyearCost;//上年度支出
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
