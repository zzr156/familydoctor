package com.ylz.bizDo.register.po;

import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 医保收费项目
 */
@Entity
@Table(name="APP_HEALTHCARE_CHARGES_PROJECT")
public class HealthcareChargesProject extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;//内部id

    @Column(name = "HCP_HEALTH_CARE_CENTER_TYPE", length = 10)
    private String hcCenterType;//医保中心类别

    @Column(name = "HCP_PROJECT_ID", length = 36)
    private String projectId;//项目编号

    @Column(name = "HCP_INPATIENT_INVOICE_PROJECT", length = 36)
    private String inpatientInvoiceProject;//住院发票项目

    @Column(name = "HCP_OUTPATIENT_INVOICE_PROJECT", length = 36)
    private String outpatientInvoiceProject;//门诊发票项目

    @Column(name = "HCP_PROJECT_NAME", length = 255)
    private String projectName;//项目名称

    @Column(name = "HCP_DRUG_DOSAGE_FORM", length = 36)
    private String drugDosageForm;//药品剂型

    @Column(name = "HCP_PROJECT_UNITS", length = 36)
    private String projectUnits;//项目单位

    @Column(name = "HCP_CHARGE_AMOUNT", length = 12)
    private BigDecimal chargeAmount;//收费金额

    @Column(name = "HCP_ELF_PAYMENT_RATIO", length = 12)
    private double elfPaymentRatio = 0; //自付比率

    @Column(name = "HCP_AVAILABLE", length = 10)
    private String available = "Y";//是否有效

    @Column(name = "HCP_MEDICAL_INSURANCE_PROJECT", length = 10)
    private String medicalInsuranceProject = "Y";//是否医保项目

    @Column(name = "HCP_HEALTHCARE_TYPE", length = 10)
    private String hcType;//医保类别

    @Column(name = "HCP_FIRST_CODE_OF_PHONETIC_ALPHABET", length = 36)
    private String firstCodeOfPhoneticAlphabet;//拼音首码

    @Column(name = "HCP_FIVE_STROKE_CODE", length = 36)
    private String fiveStrokeCode;//五笔首码

    @Column(name = "HCP_UPPER_LIMIT_OF_CHARGE", length = 12)
    private BigDecimal upperLimitOfCharge;//收费上限

    @Column(name = "HCP_MANUFACTURER", length = 255)
    private String manufacturer;//生产厂家

    @Column(name = "HCP_PROJECT_SPECIFICATIONS", length = 255)
    private String projectSpecifications;//项目规格

    public HealthcareChargesProject() {
    }

    public HealthcareChargesProject(String hcCenterType, String projectId, String inpatientInvoiceProject, String outpatientInvoiceProject, String projectName, String drugDosageForm, String projectUnits, BigDecimal chargeAmount, double elfPaymentRatio, String available, String medicalInsuranceProject, String hcType, String firstCodeOfPhoneticAlphabet, String fiveStrokeCode, BigDecimal upperLimitOfCharge, String manufacturer, String projectSpecifications) {
        this.hcCenterType = hcCenterType;
        this.projectId = projectId;
        this.inpatientInvoiceProject = inpatientInvoiceProject;
        this.outpatientInvoiceProject = outpatientInvoiceProject;
        this.projectName = projectName;
        this.drugDosageForm = drugDosageForm;
        this.projectUnits = projectUnits;
        this.chargeAmount = chargeAmount;
        this.elfPaymentRatio = elfPaymentRatio;
        this.available = available;
        this.medicalInsuranceProject = medicalInsuranceProject;
        this.hcType = hcType;
        this.firstCodeOfPhoneticAlphabet = firstCodeOfPhoneticAlphabet;
        this.fiveStrokeCode = fiveStrokeCode;
        this.upperLimitOfCharge = upperLimitOfCharge;
        this.manufacturer = manufacturer;
        this.projectSpecifications = projectSpecifications;
    }

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
