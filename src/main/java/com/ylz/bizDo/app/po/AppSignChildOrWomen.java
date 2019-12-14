package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**儿童或妇女出院信息表
 * Created by zzl on 2017/11/8.
 */
@Entity
@Table(name = "APP_SIGN_CHILD_OR_WOMEN")
public class AppSignChildOrWomen extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "SCOW_SIGN_ID",length = 36)
    private String scowSignId;//签约单id
    @Column(name = "SCOW_PATIENT_ID",length = 36)
    private String scowPatientId;//用户id
    @Column(name = "SCOW_PATIENT_IDNO",length = 36)
    private String scowPatientIdNo;//用户身份证
    @Column(name = "SCOW_OUT_HOSPITAL_DATE")
    private Calendar scowOutHospitalDate;//出院日期
    @Column(name = "SCOW_CHILDBIRTH_DATE")
    private Calendar scowChildBirthDate;//分娩日期
    @Column(name = "SCOW_LAST_MENSTRUAL_DATE")
    private Calendar scowLastMenstrualDate;//末次月经
    @Column(name = "SCOW_EXPECT_13WEEK_DATE")
    private Calendar scowExpect13Weeks;//预计孕十三周
    @Column(name = "SCOW_PREGNANCY_13WEEK_DATE")
    private Calendar scowpregnancy13Weeks;//实际孕十三周
    @Column(name = "SCOW_TYPE",length = 10)
    private String scowType;//类型 1儿童 2妇女

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScowSignId() {
        return scowSignId;
    }

    public void setScowSignId(String scowSignId) {
        this.scowSignId = scowSignId;
    }

    public String getScowPatientId() {
        return scowPatientId;
    }

    public void setScowPatientId(String scowPatientId) {
        this.scowPatientId = scowPatientId;
    }

    public Calendar getScowOutHospitalDate() {
        return scowOutHospitalDate;
    }

    public void setScowOutHospitalDate(Calendar scowOutHospitalDate) {
        this.scowOutHospitalDate = scowOutHospitalDate;
    }

    public Calendar getScowChildBirthDate() {
        return scowChildBirthDate;
    }

    public void setScowChildBirthDate(Calendar scowChildBirthDate) {
        this.scowChildBirthDate = scowChildBirthDate;
    }

    public String getScowType() {
        return scowType;
    }

    public void setScowType(String scowType) {
        this.scowType = scowType;
    }

    public String getScowPatientIdNo() {
        return scowPatientIdNo;
    }

    public void setScowPatientIdNo(String scowPatientIdNo) {
        this.scowPatientIdNo = scowPatientIdNo;
    }

    public Calendar getScowLastMenstrualDate() {
        return scowLastMenstrualDate;
    }

    public void setScowLastMenstrualDate(Calendar scowLastMenstrualDate) {
        this.scowLastMenstrualDate = scowLastMenstrualDate;
    }

    public Calendar getScowExpect13Weeks() {
        return scowExpect13Weeks;
    }

    public void setScowExpect13Weeks(Calendar scowExpect13Weeks) {
        this.scowExpect13Weeks = scowExpect13Weeks;
    }

    public Calendar getScowpregnancy13Weeks() {
        return scowpregnancy13Weeks;
    }

    public void setScowpregnancy13Weeks(Calendar scowpregnancy13Weeks) {
        this.scowpregnancy13Weeks = scowpregnancy13Weeks;
    }
}
