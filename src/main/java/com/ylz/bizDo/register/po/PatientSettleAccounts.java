package com.ylz.bizDo.register.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 病人结账信息
 */
@Entity
@Table(name="APP_PATIENT_SETTLE_ACCOUNTS")
public class PatientSettleAccounts extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;//门诊病人结帐单流水号

    @Column(name = "PSA_PATIENT_COST_ID", length = 36)
    private String patientCostID;//病人费用表主键

    @Column(name = "PSA_HOSPITAL_ID", length = 36)
    private String hospitalId;//医院、社区卫生院id

    @Column(name = "PSA_HEALTHCARE_DOCUMENT_BILL_ID", length = 36)
    private String hcDocumentBillId ="0";//医保返回的单据号

    @Column(name = "PSA_PATIENT_ID", length = 36)
    private String patientId;//病人id

    @Column(name = "PSA_TOTAL_AMOUNT", length = 12)
    private BigDecimal totalAmount;//合计金额

    @Column(name = "PSA_PERSONAL_ACCOUNT_PAYMENT", length = 12)
    private BigDecimal perAccountPayment;//个人医疗帐户支付(医保返回)

    @Column(name = "PSA_FUND_PAYMENT", length = 12)
    private BigDecimal fundPaymen;//统筹基金支付(医保返回)

    @Column(name = "PSA_ONESELF_PAYMENT", length = 12)
    private BigDecimal oneselfPayment;//自付金额(医保返回)

    @Column(name = "PSA_PAPER_ID", length = 36)
    private String paperId = "0";//打印的票据流水号

    @Column(name = "PSA_CHECK_OUT_DATE", length = 10)
    private String checkOutDate;//结帐日期

    @Column(name = "PSA_CHECK_OUT_TIME", length = 10)
    private String checkOutTime;//结帐时间

    @Column(name = "PSA_CHECKOUT_OPERATOR_ID", length = 36)
    private String checkoutOperatorId;//结帐操作员编码

    @Column(name = "PSA_HEALTHCARE_CHECK_OUT_DATE", length = 10)
    private String hcCheckOutDate;//医保结账日期

    @Column(name = "PSA_HEALTHCARE_CHECK_OUT_TIME", length = 10)
    private String hcCheckOutTime;//医保结账时间

    @Column(name = "PSA_BUSINESS_INSURANCE_PERSONAL_ACCOUNT", length = 12)
    private BigDecimal bipAccount = new BigDecimal("0");//商保个人账户

    @Column(name = "PSA_COMMERCIA_INSURANCE_CO_ORDINATE_FUND", length = 12)
    private BigDecimal cicoofund = new BigDecimal("0");//商保统筹基金

    @Column(name = "PSA_HEALTHCARE_CASH_PAYMENT", length = 12)
    private BigDecimal hecCashPayment = new BigDecimal("0");//医保现金支付

    @Column(name = "PSA_WRITE_OFF_CHECKOUT_ID", length = 36)
    private String writeOffCheckoutId = "0";//冲销结帐单号

    @Column(name = "PSA_HEALTHCARE_ACCOUNT_BALANCE", length = 12)
    private BigDecimal hcAccountBalance = new BigDecimal("0");//医保帐户余额

    @Column(name = "PSA_HEALTH_CARE_CENTER_TYPE", length = 10)
    private String hcCenterType;//医保中心类别

    @Column(name = "PSA_FUND_PAYMENT_AMOUNT", length = 12)
    private BigDecimal fundPaymentAmount = new BigDecimal("0");//基金支付金额

    @Column(name = "PSA_COMMERCIAL_INSURANCE_PAYMENT", length = 12)
    private BigDecimal commercialInsurancePayment = new BigDecimal("0");//商业保险支付

    @Column(name = "PSA_SETTLEMENT_STATUS", length = 10)
    private String settlementStatus = "0";//0:正常结算 1：冲销或被冲销

    @Column(name = "PSA_HEALTH_CARE_REGISTRATION_ID", length = 36)
    private String hcRegistrationId = "0";//医保挂号流水号,由医保程序生成的挂号流水号

    @Column(name = "PSA_REGISTERED_DEPARTMEN_NAME", length = 36)
    private String registeredDepartmenName;//挂号科室名称

    public PatientSettleAccounts() {
    }

    public PatientSettleAccounts(String patientCostID, String hospitalId, String hcDocumentBillId, String patientId, BigDecimal totalAmount, BigDecimal perAccountPayment, BigDecimal fundPaymen, BigDecimal oneselfPayment, String paperId, String checkOutDate, String checkOutTime, String checkoutOperatorId, String hcCheckOutDate, String hcCheckOutTime, BigDecimal bipAccount, BigDecimal cicoofund, BigDecimal hecCashPayment, String writeOffCheckoutId, BigDecimal hcAccountBalance, String hcCenterType, BigDecimal fundPaymentAmount, BigDecimal commercialInsurancePayment, String settlementStatus, String hcRegistrationId, String registeredDepartmenName) {
        this.patientCostID = patientCostID;
        this.hospitalId = hospitalId;
        this.hcDocumentBillId = hcDocumentBillId;
        this.patientId = patientId;
        this.totalAmount = totalAmount;
        this.perAccountPayment = perAccountPayment;
        this.fundPaymen = fundPaymen;
        this.oneselfPayment = oneselfPayment;
        this.paperId = paperId;
        this.checkOutDate = checkOutDate;
        this.checkOutTime = checkOutTime;
        this.checkoutOperatorId = checkoutOperatorId;
        this.hcCheckOutDate = hcCheckOutDate;
        this.hcCheckOutTime = hcCheckOutTime;
        this.bipAccount = bipAccount;
        this.cicoofund = cicoofund;
        this.hecCashPayment = hecCashPayment;
        this.writeOffCheckoutId = writeOffCheckoutId;
        this.hcAccountBalance = hcAccountBalance;
        this.hcCenterType = hcCenterType;
        this.fundPaymentAmount = fundPaymentAmount;
        this.commercialInsurancePayment = commercialInsurancePayment;
        this.settlementStatus = settlementStatus;
        this.hcRegistrationId = hcRegistrationId;
        this.registeredDepartmenName = registeredDepartmenName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientCostID() {
        return patientCostID;
    }

    public void setPatientCostID(String patientCostID) {
        this.patientCostID = patientCostID;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHcDocumentBillId() {
        return hcDocumentBillId;
    }

    public void setHcDocumentBillId(String hcDocumentBillId) {
        this.hcDocumentBillId = hcDocumentBillId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPerAccountPayment() {
        return perAccountPayment;
    }

    public void setPerAccountPayment(BigDecimal perAccountPayment) {
        this.perAccountPayment = perAccountPayment;
    }

    public BigDecimal getFundPaymen() {
        return fundPaymen;
    }

    public void setFundPaymen(BigDecimal fundPaymen) {
        this.fundPaymen = fundPaymen;
    }

    public BigDecimal getOneselfPayment() {
        return oneselfPayment;
    }

    public void setOneselfPayment(BigDecimal oneselfPayment) {
        this.oneselfPayment = oneselfPayment;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getCheckoutOperatorId() {
        return checkoutOperatorId;
    }

    public void setCheckoutOperatorId(String checkoutOperatorId) {
        this.checkoutOperatorId = checkoutOperatorId;
    }

    public String getHcCheckOutDate() {
        return hcCheckOutDate;
    }

    public void setHcCheckOutDate(String hcCheckOutDate) {
        this.hcCheckOutDate = hcCheckOutDate;
    }

    public String getHcCheckOutTime() {
        return hcCheckOutTime;
    }

    public void setHcCheckOutTime(String hcCheckOutTime) {
        this.hcCheckOutTime = hcCheckOutTime;
    }

    public BigDecimal getBipAccount() {
        return bipAccount;
    }

    public void setBipAccount(BigDecimal bipAccount) {
        this.bipAccount = bipAccount;
    }

    public BigDecimal getCicoofund() {
        return cicoofund;
    }

    public void setCicoofund(BigDecimal cicoofund) {
        this.cicoofund = cicoofund;
    }

    public BigDecimal getHecCashPayment() {
        return hecCashPayment;
    }

    public void setHecCashPayment(BigDecimal hecCashPayment) {
        this.hecCashPayment = hecCashPayment;
    }

    public String getWriteOffCheckoutId() {
        return writeOffCheckoutId;
    }

    public void setWriteOffCheckoutId(String writeOffCheckoutId) {
        this.writeOffCheckoutId = writeOffCheckoutId;
    }

    public BigDecimal getHcAccountBalance() {
        return hcAccountBalance;
    }

    public void setHcAccountBalance(BigDecimal hcAccountBalance) {
        this.hcAccountBalance = hcAccountBalance;
    }

    public String getHcCenterType() {
        return hcCenterType;
    }

    public void setHcCenterType(String hcCenterType) {
        this.hcCenterType = hcCenterType;
    }

    public BigDecimal getFundPaymentAmount() {
        return fundPaymentAmount;
    }

    public void setFundPaymentAmount(BigDecimal fundPaymentAmount) {
        this.fundPaymentAmount = fundPaymentAmount;
    }

    public BigDecimal getCommercialInsurancePayment() {
        return commercialInsurancePayment;
    }

    public void setCommercialInsurancePayment(BigDecimal commercialInsurancePayment) {
        this.commercialInsurancePayment = commercialInsurancePayment;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public String getHcRegistrationId() {
        return hcRegistrationId;
    }

    public void setHcRegistrationId(String hcRegistrationId) {
        this.hcRegistrationId = hcRegistrationId;
    }

    public String getRegisteredDepartmenName() {
        return registeredDepartmenName;
    }

    public void setRegisteredDepartmenName(String registeredDepartmenName) {
        this.registeredDepartmenName = registeredDepartmenName;
    }
}
