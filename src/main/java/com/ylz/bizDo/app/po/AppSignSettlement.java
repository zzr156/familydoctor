package com.ylz.bizDo.app.po;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ylz.packaccede.util.JackCalender;
import com.ylz.packcommon.common.bean.BasePO;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * 医保签约结算
 * Created by WangCheng on 2019/03/11.
 */
@Entity
@Table(name = "APP_SIGN_SETTLEMENT")
public class AppSignSettlement extends BasePO{

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键

    @Column(name = "PATIENT_NAME")
    private String patientName;//姓名

    @Column(name = "PATIENT_ID_NO")
    private String patientIdNo;//身份证号

    @Column(name = "PATIENT_CARD")
    private String patientCard;//社保卡号

    @Column(name = "SIGN_DR_Name")
    private String signDrName;//签约医生姓名

    @Column(name = "SIGN_HOSP_ID")
    private String signHospId;//签约机构

    @Column(name = "SIGN_ADDR_NAME")
    private String signAddrName;//参保人所属分中心名称

    @Column(name = "SIGN_ADDR_UNIT")
    private String signAddrUnit;//参保单位/乡村组

    @Column(name = "SIGN_FROM_DATE")
    private String signFromDate;//开始时间

    @Column(name = "SIGN_END_DATE")
    private String signEndDate;//截止时间

    @Column(name = "TRANSACTION_CODE")
    private String transactionCode;//交易码

    @Column(name = "SIGN_SERVICE_PAY")
    private BigDecimal signServicePay;//签约服务费

    @Column(name = "FUND_PAY")
    private BigDecimal fundPay;//基金支付金额

    @Column(name = "ACCOUNT_PAY")
    private BigDecimal accountPay;//账户支付金额

    @Column(name = "PERSON_PAY")
    private BigDecimal personPay;//个人支付金额

    @Column(name = "PUBLIC_PAY")
    private BigDecimal publicPay;//公卫支付金额

    @Column(name = "PRINT_STATE")
    private String printState = "0";//打印状态(1已打印)


    //医保签约返回字段记录
    @Column(name = "SIGN_ID")
    private String signId;//签约id

    @Column(name = "YI_BAO_NO")
    private String yiBaoNo;//医保签约流水号

    @Column(name = "SIGN_PAY_DATE")
    private String signPayDate;//签约医保实际收费时间



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientIdNo() {
        return patientIdNo;
    }

    public void setPatientIdNo(String patientIdNo) {
        this.patientIdNo = patientIdNo;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getSignDrName() {
        return signDrName;
    }

    public void setSignDrName(String signDrName) {
        this.signDrName = signDrName;
    }

    public String getSignHospId() {
        return signHospId;
    }

    public void setSignHospId(String signHospId) {
        this.signHospId = signHospId;
    }

    public String getSignAddrName() {
        return signAddrName;
    }

    public void setSignAddrName(String signAddrName) {
        this.signAddrName = signAddrName;
    }

    public String getSignAddrUnit() {
        return signAddrUnit;
    }

    public void setSignAddrUnit(String signAddrUnit) {
        this.signAddrUnit = signAddrUnit;
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(String signFromDate) {
        this.signFromDate = signFromDate;
    }

    public String getSignEndDate() {
        return signEndDate;
    }

    public void setSignEndDate(String signEndDate) {
        this.signEndDate = signEndDate;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public BigDecimal getSignServicePay() {
        return signServicePay;
    }

    public void setSignServicePay(BigDecimal signServicePay) {
        this.signServicePay = signServicePay;
    }

    public BigDecimal getFundPay() {
        return fundPay;
    }

    public void setFundPay(BigDecimal fundPay) {
        this.fundPay = fundPay;
    }

    public BigDecimal getAccountPay() {
        return accountPay;
    }

    public void setAccountPay(BigDecimal accountPay) {
        this.accountPay = accountPay;
    }

    public BigDecimal getPersonPay() {
        return personPay;
    }

    public void setPersonPay(BigDecimal personPay) {
        this.personPay = personPay;
    }

    public BigDecimal getPublicPay() {
        return publicPay;
    }

    public void setPublicPay(BigDecimal publicPay) {
        this.publicPay = publicPay;
    }

    public String getPrintState() {
        return printState;
    }

    public void setPrintState(String printState) {
        this.printState = printState;
    }

    public String getYiBaoNo() {
        return yiBaoNo;
    }

    public void setYiBaoNo(String yiBaoNo) {
        this.yiBaoNo = yiBaoNo;
    }

    public String getSignPayDate() {
        return signPayDate;
    }

    public void setSignPayDate(String signPayDate) {
        this.signPayDate = signPayDate;
    }
}