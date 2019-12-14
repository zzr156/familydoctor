package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by lenovo on 2017/12/27.
 * 本年度及门诊次数表
 */
@Entity
@Table(name = "APP_OUTPATIENT_NUMBER")
public class AppOutpatientNumber extends BasePO {


    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "OUTPATIENT_IDNO",  length = 36)
    private String outpatientIdno;//居民身份证
    @Column(name = "OUTPATIENT_HOSP_LEVEL_ONE" ,  length = 12)
    private String outpatientHospLevelOne;//医院等级
    @Column(name = "OUTPATIENT_HOSP_LEVEL_TWO" ,  length = 12)
    private String outpatientHospLevel_Tow;//医院等级
    @Column(name = "OUTPATIENT_HOSP_LEVEL_THREE" ,  length = 12)
    private String outpatientHospLevel_Three;//医院等级
    @Column(name = "OUTPATIENT_DOCTOR_NUMBER" ,  length = 12)
    private String outpatientDoctorNumber;//就诊次数
    @Column(name = "OUTPATIENT_FUND_COST" ,  length = 12)
    private String outpatientFundCost;//基金金额
    @Column(name = "OUTPATIENT_DOCTOR_DATE")
    private Calendar outpatientDoctorDate;//就诊时间
    @Column(name = "OUTPATIENT_PATIENT_ID" ,  length = 12)
    private String outpatientPatientId;//居民id
    @Column(name = "OUTPATIENT_CREATEDATE" )
    private Calendar outpatientCreateDate;//
    @Column(name = "OUTPATIENT_UPDATE" )
    private Calendar outpatientUpdate;//


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

    public String getOutpatientHospLevelOne() {
        return outpatientHospLevelOne;
    }

    public void setOutpatientHospLevelOne(String outpatientHospLevelOne) {
        this.outpatientHospLevelOne = outpatientHospLevelOne;
    }

    public String getOutpatientHospLevel_Tow() {
        return outpatientHospLevel_Tow;
    }

    public void setOutpatientHospLevel_Tow(String outpatientHospLevel_Tow) {
        this.outpatientHospLevel_Tow = outpatientHospLevel_Tow;
    }

    public String getOutpatientHospLevel_Three() {
        return outpatientHospLevel_Three;
    }

    public void setOutpatientHospLevel_Three(String outpatientHospLevel_Three) {
        this.outpatientHospLevel_Three = outpatientHospLevel_Three;
    }

    public String getOutpatientDoctorNumber() {
        return outpatientDoctorNumber;
    }

    public void setOutpatientDoctorNumber(String outpatientDoctorNumber) {
        this.outpatientDoctorNumber = outpatientDoctorNumber;
    }

    public String getOutpatientFundCost() {
        return outpatientFundCost;
    }

    public void setOutpatientFundCost(String outpatientFundCost) {
        this.outpatientFundCost = outpatientFundCost;
    }

    public Calendar getOutpatientDoctorDate() {
        return outpatientDoctorDate;
    }

    public void setOutpatientDoctorDate(Calendar outpatientDoctorDate) {
        this.outpatientDoctorDate = outpatientDoctorDate;
    }

    public String getOutpatientPatientId() {
        return outpatientPatientId;
    }

    public void setOutpatientPatientId(String outpatientPatientId) {
        this.outpatientPatientId = outpatientPatientId;
    }

    public Calendar getOutpatientCreateDate() {
        return outpatientCreateDate;
    }

    public void setOutpatientCreateDate(Calendar outpatientCreateDate) {
        this.outpatientCreateDate = outpatientCreateDate;
    }

    public Calendar getOutpatientUpdate() {
        return outpatientUpdate;
    }

    public void setOutpatientUpdate(Calendar outpatientUpdate) {
        this.outpatientUpdate = outpatientUpdate;
    }
}
