package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by lenovo on 2017/12/27.
 * 医保上年度支出费用
 */
@Entity
@Table(name = "APP_OUTPATIENT_COST")
public class AppOutpatientCost extends BasePO {


    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "OUTPATIENT_IDNO", nullable = false, length = 18)
    private String outpatientIdno;//居民身份证
    @Column(name = "OUTPATIENT_FORM_DATE")
    private Calendar outpatientFormDate;//开始时间
    @Column(name = "OUTPATIENT_TO_DATE")
    private Calendar outpatientToDate;//截止时间
    @Column(name = "OUTPATIENT_FUND_COST", length = 18)
    private String outpatientFundCost;//基金金额
    @Column(name = "OUTPATIENT_PATIENT_ID", length = 36)
    private String outpatientPatientId;//居民id
    @Column(name = "OUTPATIENT_CREATEDATE")
    private Calendar outpatient_createdate;//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutpatientIdno() {
        return outpatientIdno;
    }

    public void setOutpatientIdno(String outpatientIdno) {
        this.outpatientIdno = outpatientIdno;
    }

    public Calendar getOutpatientFormDate() {
        return outpatientFormDate;
    }

    public void setOutpatientFormDate(Calendar outpatientFormDate) {
        this.outpatientFormDate = outpatientFormDate;
    }

    public Calendar getOutpatientToDate() {
        return outpatientToDate;
    }

    public void setOutpatientToDate(Calendar outpatientToDate) {
        this.outpatientToDate = outpatientToDate;
    }

    public String getOutpatientFundCost() {
        return outpatientFundCost;
    }

    public void setOutpatientFundCost(String outpatientFundCost) {
        this.outpatientFundCost = outpatientFundCost;
    }

    public String getOutpatientPatientId() {
        return outpatientPatientId;
    }

    public void setOutpatientPatientId(String outpatientPatientId) {
        this.outpatientPatientId = outpatientPatientId;
    }

    public Calendar getOutpatient_createdate() {
        return outpatient_createdate;
    }

    public void setOutpatient_createdate(Calendar outpatient_createdate) {
        this.outpatient_createdate = outpatient_createdate;
    }
}
