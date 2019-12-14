package com.ylz.bizDo.app.po;


import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 用药提醒
 */
@Entity
@Table(name = "APP_DRUG_PLAN")
public class AppDrugPlan extends BasePO {
    // Fields
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "DP_PATIENT_ID", length =36 )
    private String dpPatientId;//患者id
    @Column(name = "DP_REMIND_TIME", length =36 )
    private String dpRemindTime;//提醒时间
    @Column(name = "DP_CREATE_TIME")
    private Calendar dpCreateTime;//创建时间
    @Column(name = "DP_STATE",length = 10)
    private String dpState;//1开启，0关闭
    @Column(name = "MONDAY", length =10 )
    private String monday;
    @Column(name = "TUESDAY", length =10 )
    private String tuesday;
    @Column(name = "WEDNESDAY", length =10 )
    private String wednesday;
    @Column(name = "THURSDAY", length =10 )
    private String thursday;
    @Column(name = "FRIDAY", length =10 )
    private String friday;
    @Column(name = "SATURDAY", length =10 )
    private String saturday;
    @Column(name = "SUNDAY", length =10 )
    private String sunday;
    @Column(name = "ONLY_ONE", length =10 )
    private String onlyOne;//只提醒一次

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDpPatientId() {
        return dpPatientId;
    }

    public void setDpPatientId(String dpPatientId) {
        this.dpPatientId = dpPatientId;
    }


    public String getDpRemindTime() {
        return dpRemindTime;
    }

    public void setDpRemindTime(String dpRemindTime) {
        this.dpRemindTime = dpRemindTime;
    }

    public Calendar getDpCreateTime() {
        return dpCreateTime;
    }

    public void setDpCreateTime(Calendar dpCreateTime) {
        this.dpCreateTime = dpCreateTime;
    }

    public String getDpState() {
        return dpState;
    }

    public void setDpState(String dpState) {
        this.dpState = dpState;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getOnlyOne() {
        return onlyOne;
    }

    public void setOnlyOne(String onlyOne) {
        this.onlyOne = onlyOne;
    }

}
