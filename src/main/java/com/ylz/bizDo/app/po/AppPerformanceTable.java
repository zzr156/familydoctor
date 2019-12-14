package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 履约数据表 类型14公共服务
 * Created by zzl on 2018/5/25.
 */
@Entity
@Table(name="APP_PERFORMANCE_TABLE")
public class AppPerformanceTable extends BasePO {
    @Id
    @Column(name="ID", unique=true, nullable=false, length=36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "PER_PATIENT_NAME",length = 50)
    private String perPatientName;//患者姓名
    @Column(name = "PER_PATIENT_IDNO",length = 18)
    private String perPatientIdNo;//患者身份证
    @Column(name = "PER_AREA_CODE",length = 12)
    private String perAreaCode;//接收地市行政区划编码
    @Column(name = "PER_SERM_VALUE",length = 20)
    private String perSermValue;//服务包编号
    @Column(name = "PER_SERG_VALUE",length = 20)
    private String perSergValue;//服务组合编号
    @Column(name = "PER_SERPK_VALUE",length = 20)
    private String perSerpkValue;//服务内容编号
    @Column(name = "PER_SERM_CONTENT")
    private String perSermContent;//服务内容
    @Column(name = "PER_SERVE_DATE")
    private Calendar perServeDate;//服务时间
    @Column(name = "PER_SERVE_NUM",length = 10)
    private String perServeNum;//服务次数
    @Column(name = "PER_SOURCE",length = 10)
    private String perSource;//来源（1基卫）
    @Column(name = "PER_HOSP_ID",length = 36)
    private String perHospId;//机构主键
    @Column(name="PER_HOSP_NAME", length=45)
    private String perHospName;//医院名字
    @Column(name = "PER_DR_ID",length = 36)
    private String perDrId;//医生主键
    @Column(name="PER_DR_NAME", length=25)
    private String perDrName;//医生名字
    @Column(name = "PER_TEAM_ID",length = 36)
    private String perTeamId;//团队主键
    @Column(name="PER_YEAR", length=10)
    private String perYear;//年
    @Column(name="PER_MONTH", length=10)
    private String perMonth;//月
    @Column(name="PER_YEAR_MONTH", length=10)
    private String perYearMonth;//年月
    @Column(name="PER_FOREIGN", length=36)
    private String perForeign;//主键
    @Column(name = "PER_TYPE",length = 10)
    private String perType;//类型1:居民健康档案2:健康教育3:健康指导4:定期随访5:健康咨询6:预防接种7:中医健康指导(0-36月龄)8:孕期保健服务9:产后访视10:中医体质辨识11:中医药保健指导12:健康体检13:用药指导 14:公共服务
    @Column(name="PER_CREATE_DATE")
    private Calendar perCreateDate;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPerPatientName() {
        return perPatientName;
    }

    public void setPerPatientName(String perPatientName) {
        this.perPatientName = perPatientName;
    }

    public String getPerPatientIdNo() {
        return perPatientIdNo;
    }

    public void setPerPatientIdNo(String perPatientIdNo) {
        this.perPatientIdNo = perPatientIdNo;
    }

    public String getPerAreaCode() {
        return perAreaCode;
    }

    public void setPerAreaCode(String perAreaCode) {
        this.perAreaCode = perAreaCode;
    }

    public String getPerSermValue() {
        return perSermValue;
    }

    public void setPerSermValue(String perSermValue) {
        this.perSermValue = perSermValue;
    }

    public String getPerSergValue() {
        return perSergValue;
    }

    public void setPerSergValue(String perSergValue) {
        this.perSergValue = perSergValue;
    }

    public String getPerSerpkValue() {
        return perSerpkValue;
    }

    public void setPerSerpkValue(String perSerpkValue) {
        this.perSerpkValue = perSerpkValue;
    }

    public String getPerSermContent() {
        return perSermContent;
    }

    public void setPerSermContent(String perSermContent) {
        this.perSermContent = perSermContent;
    }

    public Calendar getPerServeDate() {
        return perServeDate;
    }

    public void setPerServeDate(Calendar perServeDate) {
        this.perServeDate = perServeDate;
    }

    public String getPerServeNum() {
        return perServeNum;
    }

    public void setPerServeNum(String perServeNum) {
        this.perServeNum = perServeNum;
    }

    public String getPerSource() {
        return perSource;
    }

    public void setPerSource(String perSource) {
        this.perSource = perSource;
    }

    public String getPerHospId() {
        return perHospId;
    }

    public void setPerHospId(String perHospId) {
        this.perHospId = perHospId;
    }

    public String getPerDrId() {
        return perDrId;
    }

    public void setPerDrId(String perDrId) {
        this.perDrId = perDrId;
    }

    public String getPerTeamId() {
        return perTeamId;
    }

    public void setPerTeamId(String perTeamId) {
        this.perTeamId = perTeamId;
    }

    public String getPerHospName() {
        return perHospName;
    }

    public void setPerHospName(String perHospName) {
        this.perHospName = perHospName;
    }

    public String getPerDrName() {
        return perDrName;
    }

    public void setPerDrName(String perDrName) {
        this.perDrName = perDrName;
    }

    public String getPerYear() {
        return perYear;
    }

    public void setPerYear(String perYear) {
        this.perYear = perYear;
    }

    public String getPerMonth() {
        return perMonth;
    }

    public void setPerMonth(String perMonth) {
        this.perMonth = perMonth;
    }

    public String getPerYearMonth() {
        return perYearMonth;
    }

    public void setPerYearMonth(String perYearMonth) {
        this.perYearMonth = perYearMonth;
    }

    public String getPerForeign() {
        return perForeign;
    }

    public void setPerForeign(String perForeign) {
        this.perForeign = perForeign;
    }

    public String getPerType() {
        return perType;
    }

    public void setPerType(String perType) {
        this.perType = perType;
    }

    public Calendar getPerCreateDate() {
        return perCreateDate;
    }

    public void setPerCreateDate(Calendar perCreateDate) {
        this.perCreateDate = perCreateDate;
    }
}
