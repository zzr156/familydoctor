package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * Created by zzl on 2017/8/28.
 */
public class AppDrDrugGuideEntity {
    private String id;//指导id
    private String doctorId;//指导医生
    private Timestamp guideTime;//指导日期
    private String drugName;//药品名称
    private String drugType;//药品类型
    private String drugPeriod;//用药周期
    private String drugFrequency;//用药频次
    private String drugTaking;//服用时段
    private String drugUsage;//服用方式
    private String doctorName;//医生姓名
    private String frequencyOther;//频次其他
    private String periodOther;//周期其他
    private Timestamp drugBeginTime;//用药开始时间
    private String drugId;//药品id
    private String note;//备注
    private String dgSpec;//规格
    private String dgBatch;//批号
    private String dgUseLevel;//用量
    private String dgDosageUnit;//用量单位
    private String dgAllUseLevel;//总量
    private String dgAllDosageUnit;//总量单位


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getGuideTime() {
        return ExtendDate.getYMD_h_m(guideTime);
    }

    public void setGuideTime(Timestamp guideTime) {
        this.guideTime = guideTime;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugType() throws Exception {
        if(StringUtils.isNotBlank(this.drugType)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_DRUGTYPE,this.drugType);
            if(value != null){
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDrugPeriod() {
     /*   if(StringUtils.isNotBlank(this.drugPeriod)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_PERIOD,this.drugPeriod);
            if(value != null){
                return value.getCodeTitle();
            }
        }*/
        return drugPeriod;
    }

    public void setDrugPeriod(String drugPeriod) {
        this.drugPeriod = drugPeriod;
    }

    public String getDrugFrequency() {
    /*    if(StringUtils.isNotBlank(this.drugFrequency)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FREQUENCY,this.drugFrequency);
            if(value != null){
                return value.getCodeTitle();
            }
        }*/
        return drugFrequency;
    }

    public void setDrugFrequency(String drugFrequency) {
        this.drugFrequency = drugFrequency;
    }

    public String getDrugTaking() throws Exception {
        if(StringUtils.isNotBlank(this.drugTaking)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TAKING,this.drugTaking);
            if(value != null){
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public void setDrugTaking(String drugTaking) {
        this.drugTaking = drugTaking;
    }

    public String getDrugUsage() throws Exception {
        if(StringUtils.isNotBlank(this.drugUsage)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_USAGE,this.drugUsage);
            if(value != null){
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public void setDrugUsage(String drugUsage) {
        this.drugUsage = drugUsage;
    }


    public String getDoctorName(){
        if(this.doctorId!=null){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser doc = (AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.doctorId);
            if(doc!=null){
                return doc.getDrName();
            }
        }
        return "";
    }

    public String getFrequencyOther() {
        return frequencyOther;
    }

    public void setFrequencyOther(String frequencyOther) {
        this.frequencyOther = frequencyOther;
    }

    public String getPeriodOther() {
        return periodOther;
    }

    public void setPeriodOther(String periodOther) {
        this.periodOther = periodOther;
    }

    public String getDrugBeginTime() {
        return ExtendDate.getChineseYMD(drugBeginTime);
    }

    public void setDrugBeginTime(Timestamp drugBeginTime) {
        this.drugBeginTime = drugBeginTime;
    }

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDgSpec() {
        return dgSpec;
    }

    public void setDgSpec(String dgSpec) {
        this.dgSpec = dgSpec;
    }

    public String getDgBatch() {
        return dgBatch;
    }

    public void setDgBatch(String dgBatch) {
        this.dgBatch = dgBatch;
    }

    public String getDgUseLevel() {
        return dgUseLevel;
    }

    public void setDgUseLevel(String dgUseLevel) {
        this.dgUseLevel = dgUseLevel;
    }

    public String getDgDosageUnit() {
        return dgDosageUnit;
    }

    public void setDgDosageUnit(String dgDosageUnit) {
        this.dgDosageUnit = dgDosageUnit;

    }

    public String getDgAllUseLevel() {
        return dgAllUseLevel;
    }

    public void setDgAllUseLevel(String dgAllUseLevel) {
        this.dgAllUseLevel = dgAllUseLevel;
    }

    public String getDgAllDosageUnit() {
        return dgAllDosageUnit;
    }

    public void setDgAllDosageUnit(String dgAllDosageUnit) {
        this.dgAllDosageUnit = dgAllDosageUnit;

    }
}
