package com.ylz.bizDo.register.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.*;
import java.math.BigDecimal;
// 个人 医保套餐
@Entity
@Table(name="APP_PATIENT_PAY_FEE")
public class PatientPayFee extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;//单据流水号

    @Column(name = "PPF_HOSPITAL_ID", length = 36)
    private String hospitalId;//医院内、区卫生院id

    @Column(name = "PPF_PATIENT_ID", length = 36)
    private String patientId;//病人id号

    @Column(name = "PPF_PAY_AMOUNT", length = 36)
    private BigDecimal payAmount;//交费金额

    @Column(name = "PPF_PAYMENT_MODE", length = 36)
    private String paymentMode;//支付方式 1个人账户支付 2 统筹基金支付 3 现金支付

    @Column(name = "PPF_PAYMENT_MODE_NAME", length = 36)
    private String paymentModeName;//支付方式名称

    @Column(name = "PPF_PAPER_ID", length = 36)
    private String paperId;//打印的票据流水号

    @Column(name = "PPF_OPERATION_DATE", length = 36)
    private String operationDate;//操作日期

    @Column(name = "PPF_OPERATION_TIME", length = 36)
    private String operationTime;//操作时间

    @Column(name = "PPF_OPERATOR_ID", length = 36)
    private String operatorId;//操作员ID

    @Column(name = "PPF_OPERATOR_NAME", length = 36)
    private String operatorName;//操作员员姓名

    @Column(name = "PPF_PATIENT_BALANCE", length = 36)
    private BigDecimal patientBalance;//病人余额

    @Column(name = "PPF_PAYMENT_REMARK", length = 36)
    private String paymentRemark = "0";//交费备注(0交预交金、1、退预交金）

    public PatientPayFee() {
    }

    public PatientPayFee(String hospitalId, String patientId, BigDecimal payAmount, String paymentMode, String paymentModeName, String paperId, String operationDate, String operationTime, String operatorId, String operatorName, BigDecimal patientBalance, String paymentRemark) {
        this.hospitalId = hospitalId;
        this.patientId = patientId;
        this.payAmount = payAmount;
        this.paymentMode = paymentMode;
        this.paymentModeName = paymentModeName;
        this.paperId = paperId;
        this.operationDate = operationDate;
        this.operationTime = operationTime;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.patientBalance = patientBalance;
        this.paymentRemark = paymentRemark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentModeName() {
        return paymentModeName;
    }

    public void setPaymentModeName(String paymentModeName) {
        this.paymentModeName = paymentModeName;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public BigDecimal getPatientBalance() {
        return patientBalance;
    }

    public void setPatientBalance(BigDecimal patientBalance) {
        this.patientBalance = patientBalance;
    }

    public String getPaymentRemark() {
        return paymentRemark;
    }

    public void setPaymentRemark(String paymentRemark) {
        this.paymentRemark = paymentRemark;
    }
}
