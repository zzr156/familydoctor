package com.ylz.bizDo.register.po;

import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.hyd.Base;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 病人费用明细
 */
@Entity
@Table(name="APP_PATIENT_COST_DETAIL")
public class PatientCostDetail extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;//门诊病人费用明细流水号

    @Column(name = "PCD_COST_DOCUMENTS_ID", length = 36)
    private String costDocumentsId;//单据号（外键、费用主表的单据号）

    @Column(name = "PCD_ITEM_CODE", length = 36)
    private String itemCode;//医疗收费编码或药品编码

    @Column(name = "PCD_ITEM_NAME", length = 36)
    private String itemName;//项目名称

    @Column(name = "PCD_ITEM_SPECIFICATION", length = 36)
    private String itemSpecification;//项目规格

    @Column(name = "PCD_ITEM_UNIT", length = 36)
    private String itemUnit;//单位

    @Column(name = "PCD_UNIT_PRICE", length = 12)
    private String unitPrice;//单价

    @Column(name = "PCD_QUANTITY", length = 12)
    private String quantity;//数量

    @Column(name = "PCD_TOTAL_AMOUNT", length = 12)
    private BigDecimal totalAmount;//合计金额

    @Column(name = "PCD_ONESELF_AMOUNT", length = 12)
    private BigDecimal oneself_amount;//自费金额

    @Column(name = "PCD_BILLING_DATE", length = 10)
    private String billingDate;//开单日期

    @Column(name = "PCD_BILLING_TIME", length = 10)
    private String billingTime;//开单时间

    @Column(name = "PCD_WRITE_OFF_MARK", length = 10)
    private String writeOffMark = "Z";//Z:表示正常的没有被冲销的  +被冲销的单  -表示该记录冲销别的单

    @Column(name = "PCD_ORDER_DEPARTMENT_ID", length = 36)
    private String orderDeptId;//开单科室编号

    @Column(name = "PCD_ORDER_DOCTOR_DEPARTMENT_ID", length = 36)
    private String orderDrDepId;//开单医生科室编号

    @Column(name = "PCD_ORDER_DOCTOR_ID", length = 36)
    private String orderDoctorId;//开单医生工号

    @Column(name = "PCD_REMARK", length = 255)
    private String remark;//备注

    @Column(name = "PCD_BE_WRITE_OFF_DETAIL_ID", length = 36)
    private String beWriteOffDetailId = "0";//冲销批准人

    @Column(name = "PCD_WRITE_OFF_PEOPLE", length = 36)
    private String writeOffPeople;//冲销批准人

    @Column(name = "PCD_WRITE_OFF_CAUSE", length = 255)
    private String writeOffCause;//冲销原因

    @Column(name = "PCD_PACKAGE_ID", length = 36)
    private String packageId;//套餐id


    public PatientCostDetail() {
    }

    public PatientCostDetail(String costDocumentsId, String itemCode, String itemName, String itemSpecification, String itemUnit, String unitPrice, String quantity, BigDecimal totalAmount, BigDecimal oneself_amount, String billingDate, String billingTime, String writeOffMark, String orderDeptId, String orderDrDepId, String orderDoctorId, String remark, String beWriteOffDetailId, String writeOffPeople, String writeOffCause, String packageId) {
        this.costDocumentsId = costDocumentsId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemSpecification = itemSpecification;
        this.itemUnit = itemUnit;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.oneself_amount = oneself_amount;
        this.billingDate = billingDate;
        this.billingTime = billingTime;
        this.writeOffMark = writeOffMark;
        this.orderDeptId = orderDeptId;
        this.orderDrDepId = orderDrDepId;
        this.orderDoctorId = orderDoctorId;
        this.remark = remark;
        this.beWriteOffDetailId = beWriteOffDetailId;
        this.writeOffPeople = writeOffPeople;
        this.writeOffCause = writeOffCause;
        this.packageId = packageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCostDocumentsId() {
        return costDocumentsId;
    }

    public void setCostDocumentsId(String costDocumentsId) {
        this.costDocumentsId = costDocumentsId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSpecification() {
        return itemSpecification;
    }

    public void setItemSpecification(String itemSpecification) {
        this.itemSpecification = itemSpecification;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getOneself_amount() {
        return oneself_amount;
    }

    public void setOneself_amount(BigDecimal oneself_amount) {
        this.oneself_amount = oneself_amount;
    }

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public String getBillingTime() {
        return billingTime;
    }

    public void setBillingTime(String billingTime) {
        this.billingTime = billingTime;
    }

    public String getWriteOffMark() {
        return writeOffMark;
    }

    public void setWriteOffMark(String writeOffMark) {
        this.writeOffMark = writeOffMark;
    }

    public String getOrderDeptId() {
        return orderDeptId;
    }

    public void setOrderDeptId(String orderDeptId) {
        this.orderDeptId = orderDeptId;
    }

    public String getOrderDrDepId() {
        return orderDrDepId;
    }

    public void setOrderDrDepId(String orderDrDepId) {
        this.orderDrDepId = orderDrDepId;
    }

    public String getOrderDoctorId() {
        return orderDoctorId;
    }

    public void setOrderDoctorId(String orderDoctorId) {
        this.orderDoctorId = orderDoctorId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBeWriteOffDetailId() {
        return beWriteOffDetailId;
    }

    public void setBeWriteOffDetailId(String beWriteOffDetailId) {
        this.beWriteOffDetailId = beWriteOffDetailId;
    }

    public String getWriteOffPeople() {
        return writeOffPeople;
    }

    public void setWriteOffPeople(String writeOffPeople) {
        this.writeOffPeople = writeOffPeople;
    }

    public String getWriteOffCause() {
        return writeOffCause;
    }

    public void setWriteOffCause(String writeOffCause) {
        this.writeOffCause = writeOffCause;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}
