package com.ylz.bizDo.register.vo;

import com.ylz.packcommon.common.CommConditionVo;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

public class HcProjectVo extends CommConditionVo {
    private String id;//内部id
    private String hcCenterType;//医保中心类别
    private String projectId;//项目编号
    private String inpatientInvoiceProject;//住院发票项目
    private String outpatientInvoiceProject;//门诊发票项目
    private String projectName;//项目名称
    private String drugDosageForm;//药品剂型
    private String projectUnits;//项目单位
    private BigDecimal chargeAmount;//收费金额
    private double elfPaymentRatio; //自付比率
    private String available;//是否有效
    private String medicalInsuranceProject;//是否医保项目
    private String hcType;//医保类别
    private String firstCodeOfPhoneticAlphabet;//拼音首码
    private String fiveStrokeCode;//五笔首码
    private BigDecimal upperLimitOfCharge;//收费上限
    private String manufacturer;//生产厂家
    private String projectSpecifications;//项目规格

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHcCenterType() {
        return hcCenterType;
    }

    public void setHcCenterType(String hcCenterType) {
        this.hcCenterType = hcCenterType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getInpatientInvoiceProject() {
        return inpatientInvoiceProject;
    }

    public void setInpatientInvoiceProject(String inpatientInvoiceProject) {
        this.inpatientInvoiceProject = inpatientInvoiceProject;
    }

    public String getOutpatientInvoiceProject() {
        return outpatientInvoiceProject;
    }

    public void setOutpatientInvoiceProject(String outpatientInvoiceProject) {
        this.outpatientInvoiceProject = outpatientInvoiceProject;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDrugDosageForm() {
        return drugDosageForm;
    }

    public void setDrugDosageForm(String drugDosageForm) {
        this.drugDosageForm = drugDosageForm;
    }

    public String getProjectUnits() {
        return projectUnits;
    }

    public void setProjectUnits(String projectUnits) {
        this.projectUnits = projectUnits;
    }

    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public double getElfPaymentRatio() {
        return elfPaymentRatio;
    }

    public void setElfPaymentRatio(double elfPaymentRatio) {
        this.elfPaymentRatio = elfPaymentRatio;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getMedicalInsuranceProject() {
        return medicalInsuranceProject;
    }

    public void setMedicalInsuranceProject(String medicalInsuranceProject) {
        this.medicalInsuranceProject = medicalInsuranceProject;
    }

    public String getHcType() {
        return hcType;
    }

    public void setHcType(String hcType) {
        this.hcType = hcType;
    }

    public String getFirstCodeOfPhoneticAlphabet() {
        return firstCodeOfPhoneticAlphabet;
    }

    public void setFirstCodeOfPhoneticAlphabet(String firstCodeOfPhoneticAlphabet) {
        this.firstCodeOfPhoneticAlphabet = firstCodeOfPhoneticAlphabet;
    }

    public String getFiveStrokeCode() {
        return fiveStrokeCode;
    }

    public void setFiveStrokeCode(String fiveStrokeCode) {
        this.fiveStrokeCode = fiveStrokeCode;
    }

    public BigDecimal getUpperLimitOfCharge() {
        return upperLimitOfCharge;
    }

    public void setUpperLimitOfCharge(BigDecimal upperLimitOfCharge) {
        this.upperLimitOfCharge = upperLimitOfCharge;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProjectSpecifications() {
        return projectSpecifications;
    }

    public void setProjectSpecifications(String projectSpecifications) {
        this.projectSpecifications = projectSpecifications;
    }
}
