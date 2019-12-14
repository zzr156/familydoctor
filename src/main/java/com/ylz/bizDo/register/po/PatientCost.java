package com.ylz.bizDo.register.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 病人费用
 */
@Entity
@Table(name = "APP_PATIENT_COST")
public class PatientCost extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;                      //病人费用单据流水号

    @Column(name = "PC_HEALTHCARE_COST_ID", length = 36)
    private String hospitalId;              //医院、社区卫生院id

    @Column(name = "PC_HOSPITAL_ID", length = 36)
    private String hcCostId = "0";                //医保接口返回的费用单据流水号

    @Column(name = "PC_INNER_REGISTER_ID", length = 40)
    private String innerRegisterId;//内部挂号号由签约ID+"_"+"ghh"组成

    @Column(name = "PC_PATIENT_ID", length = 36 )
    private String patientId;               //病人id

    @Column(name = "PC_PATIENT_NAME", length = 36)
    private String patientName;             //病人姓名

    @Column(name = "PC_TOTAL_AMOUNT", length = 12)
    private BigDecimal totalAmount;         //总金额

    @Column(name = "PC_PATIENT_BALANCE", length = 12)
    private BigDecimal patientBalance = new BigDecimal("0");      //病人余额

    @Column(name = "PC_OPERATION_DATE", length = 36)
    private String operationDate;           //操作日期

    @Column(name = "PC_OPERATION_TIME", length = 36)
    private String operationTime;           //操作时间

    @Column(name = "PC_OPERATOR_ID", length = 36)
    private String operatorId;              //操作员工编号

    @Column(name = "PC_OPERATOR_NAME", length = 36)
    private String operatorName;            //操作员姓名

    @Column(name = "PC_OPERATOR_DEPARTMENT", length = 36)
    private String operatorDepartment;      //操作员科室也可以称为收费科室

    @Column(name = "PC_SETTLEMENT_ID", length = 12)
    private String settlementId = "0";       //结算单号,0:该帐目未结算(外键、结账表结账单号）

    @Column(name = "PC_HEALTHCARE_CENTER", length = 36)
    private String hcCenter;                //医保中心类别

    @Column(name = "PC_DATA_SOURCE", length = 36)
    private String dataSource;              //数据来源

    public PatientCost() {
    }

    public PatientCost(String hospitalId, String hcCostId, String innerRegisterId, String patientId, String patientName, BigDecimal totalAmount, BigDecimal patientBalance, String operationDate, String operationTime, String operatorId, String operatorName, String operatorDepartment, String settlementId, String hcCenter, String dataSource) {
        this.hospitalId = hospitalId;
        this.hcCostId = hcCostId;
        this.innerRegisterId = innerRegisterId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.totalAmount = totalAmount;
        this.patientBalance = patientBalance;
        this.operationDate = operationDate;
        this.operationTime = operationTime;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.operatorDepartment = operatorDepartment;
        this.settlementId = settlementId;
        this.hcCenter = hcCenter;
        this.dataSource = dataSource;
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

    public String getHcCostId() {
        return hcCostId;
    }

    public void setHcCostId(String hcCostId) {
        this.hcCostId = hcCostId;
    }

    public String getInnerRegisterId() {
        return innerRegisterId;
    }

    public void setInnerRegisterId(String innerRegisterId) {
        this.innerRegisterId = innerRegisterId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPatientBalance() {
        return patientBalance;
    }

    public void setPatientBalance(BigDecimal patientBalance) {
        this.patientBalance = patientBalance;
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

    public String getOperatorDepartment() {
        return operatorDepartment;
    }

    public void setOperatorDepartment(String operatorDepartment) {
        this.operatorDepartment = operatorDepartment;
    }

    public String getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(String settlementId) {
        this.settlementId = settlementId;
    }

    public String getHcCenter() {
        return hcCenter;
    }

    public void setHcCenter(String hcCenter) {
        this.hcCenter = hcCenter;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
