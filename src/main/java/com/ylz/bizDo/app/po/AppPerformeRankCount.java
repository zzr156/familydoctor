package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 签约平台数据初始数据表
 * Created by zzl on 2018/5/29.
 */
@Entity
@Table(name="APP_PERFORME_RANK_COUNT")
public class AppPerformeRankCount extends BasePO {
    @Id
    @Column(name="ID", unique=true, nullable=false, length=36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "PRC_DIABETES_COUNT",length = 10)
    private String prcDiabetesCount;//糖尿病数
    @Column(name = "PRC_HYPERTENSION_COUNT",length = 10)
    private String prcHypertensionCount;//高血压数
    @Column(name = "PRC_HOSP_ID",length = 36)
    private String prcHospId;//医院id
    @Column(name = "PRC_DR_ID",length = 36)
    private String prcDrId;//医生主键
    @Column(name = "PRC_AREA_CODE",length = 12)
    private String prcAreaCode;//区域编码
    @Column(name = "PRC_YEAR",length = 4)
    private String prcYear;//年份
    @Column(name = "PRC_YEAR_MONTH",length = 10)
    private String prcYearMonth;//年月
    @Column(name = "PRC_MONTH",length = 2)
    private String prcMonth;//月
    @Column(name = "PRC_TEAM_ID",length = 36)
    private String prcTeamId;//团队主键
    @Column(name = "PRC_TOTAL_COUNT",length = 20)
    private String prcTotalCount;//糖尿病数+高血压数
    @Column(name = "PRC_DIABETES_PATIENT_COUNT",length = 20)
    private String prcDiabetesPatientCount;//糖尿病签约人数
    @Column(name = "PRC_HYPERTENSION_PATIENT_COUNT",length = 20)
    private String prcHypertensionPatientCount;//高血压签约人数
    @Column(name = "PRC_TOTAL_PATIENT_COUNT",length = 20)
    private String prcTotalPatientCount;//糖尿病签约人数+高血压签约人数
    @Column(name = "PRC_RATE",length = 10)
    private String prcRate;//综合率 = （糖尿病随访数+高血压随访数）/（糖尿病签约人数+高血压签约人数）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrcDiabetesCount() {
        return prcDiabetesCount;
    }

    public void setPrcDiabetesCount(String prcDiabetesCount) {
        this.prcDiabetesCount = prcDiabetesCount;
    }

    public String getPrcHypertensionCount() {
        return prcHypertensionCount;
    }

    public void setPrcHypertensionCount(String prcHypertensionCount) {
        this.prcHypertensionCount = prcHypertensionCount;
    }

    public String getPrcHospId() {
        return prcHospId;
    }

    public void setPrcHospId(String prcHospId) {
        this.prcHospId = prcHospId;
    }

    public String getPrcDrId() {
        return prcDrId;
    }

    public void setPrcDrId(String prcDrId) {
        this.prcDrId = prcDrId;
    }

    public String getPrcAreaCode() {
        return prcAreaCode;
    }

    public void setPrcAreaCode(String prcAreaCode) {
        this.prcAreaCode = prcAreaCode;
    }

    public String getPrcYear() {
        return prcYear;
    }

    public void setPrcYear(String prcYear) {
        this.prcYear = prcYear;
    }

    public String getPrcYearMonth() {
        return prcYearMonth;
    }

    public void setPrcYearMonth(String prcYearMonth) {
        this.prcYearMonth = prcYearMonth;
    }

    public String getPrcMonth() {
        return prcMonth;
    }

    public void setPrcMonth(String prcMonth) {
        this.prcMonth = prcMonth;
    }

    public String getPrcTeamId() {
        return prcTeamId;
    }

    public void setPrcTeamId(String prcTeamId) {
        this.prcTeamId = prcTeamId;
    }

    public String getPrcTotalCount() {
        return prcTotalCount;
    }

    public void setPrcTotalCount(String prcTotalCount) {
        this.prcTotalCount = prcTotalCount;
    }

    public String getPrcDiabetesPatientCount() {
        return prcDiabetesPatientCount;
    }

    public void setPrcDiabetesPatientCount(String prcDiabetesPatientCount) {
        this.prcDiabetesPatientCount = prcDiabetesPatientCount;
    }

    public String getPrcHypertensionPatientCount() {
        return prcHypertensionPatientCount;
    }

    public void setPrcHypertensionPatientCount(String prcHypertensionPatientCount) {
        this.prcHypertensionPatientCount = prcHypertensionPatientCount;
    }

    public String getPrcTotalPatientCount() {
        return prcTotalPatientCount;
    }

    public void setPrcTotalPatientCount(String prcTotalPatientCount) {
        this.prcTotalPatientCount = prcTotalPatientCount;
    }

    public String getPrcRate() {
        return prcRate;
    }

    public void setPrcRate(String prcRate) {
        this.prcRate = prcRate;
    }
}
