package com.ylz.bizDo.register.vo;

import javax.persistence.Column;
import java.math.BigDecimal;

public class RegisterListVo {
    private String mainId;//主键
    private String patientCostID;//病人费用表主键
    private String hospitalId;//医院、社区卫生院id
    private String hcDocumentBillId;//医保返回的单据号
    private String patientId;//病人id
    private BigDecimal totalAmount;//合计金额
    private BigDecimal perAccountPayment;//个人医疗帐户支付(医保返回)
    private BigDecimal fundPaymen;//统筹基金支付(医保返回)
    private BigDecimal oneselfPayment;//自付金额(医保返回)
    private String paperId;//打印的票据流水号
    private String checkOutDate;//结帐日期
    private String checkOutTime;//结帐时间
    private String checkoutOperatorId;//结帐操作员编码
    private String hcCheckOutDate;//医保结账日期
    private String hcCheckOutTime;//医保结账时间
    private BigDecimal bipAccount ;//商保个人账户
    private BigDecimal cicoofund;//商保统筹基金
    private BigDecimal hecCashPayment;//医保现金支付
    private String writeOffCheckoutId;//冲销结帐单号
    private BigDecimal hcAccountBalance;//医保帐户余额
    private String hcCenterType;//医保中心类别
    private BigDecimal fundPaymentAmount;//基金支付金额
    private BigDecimal commercialInsurancePayment;//商业保险支付
    private String settlementStatus;//0:正常结算 1：冲销或被冲销
    private String hcRegistrationId;//医保挂号流水号,由医保程序生成的挂号流水号
    private String registeredDepartmenName;//挂号科室名称
    private String resultStr;//返回的结果集
    private String count;//处方项目数
    private String singFormId;//签约ID
    private RegisterSelVo rsVo;//签约单信息Vo
    private String doctorListStr;//医生信息
    private String drCount;//签约医生数量
    private String sersmPkValues;//服务内容值
    private String signGroup;// 签约病种

    public String getSersmPkValues() {
        return sersmPkValues;
    }

    public void setSersmPkValues(String sersmPkValues) {
        this.sersmPkValues = sersmPkValues;
    }

    public String getDrCount() {
        return drCount;
    }

    public void setDrCount(String drCount) {
        this.drCount = drCount;
    }

    public String getDoctorListStr() {
        return doctorListStr;
    }

    public void setDoctorListStr(String doctorListStr) {
        this.doctorListStr = doctorListStr;
    }

    public RegisterSelVo getRsVo() {
        return rsVo;
    }

    public void setRsVo(RegisterSelVo rsVo) {
        this.rsVo = rsVo;
    }

    public String getSingFormId() {
        return singFormId;
    }

    public void setSingFormId(String singFormId) {
        this.singFormId = singFormId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
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

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public String getSignGroup() {
        return signGroup;
    }

    public void setSignGroup(String signGroup) {
        this.signGroup = signGroup;
    }
}

