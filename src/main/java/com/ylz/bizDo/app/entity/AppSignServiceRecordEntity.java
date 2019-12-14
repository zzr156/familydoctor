package com.ylz.bizDo.app.entity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressConfiguration;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by zzl on 2018/10/31.
 */
public class AppSignServiceRecordEntity {
    private String id;//主键
    private String ssrPatientId;//签约居民主键
    private String ssrPatientName;//签约居民姓名
    private String ssrServeTime;//服务时间
    private String ssrServeAddr;//服务地点
    private Integer ssrHP;//血压（高压）
    private Integer ssrLP;//血压（低压）
    private String ssrBloodSugar;//血糖值
    private String ssrContentValue;//服务内容
    private String ssrMainSituation;//主要情况告知
    private String ssrHealthGuidance;//健康指导
    private String ssrPatientAutograph;//接受服务对象签名(外网地址)
    private String ssrPatientWithin;//接受服务对象签名（内网地址）
    private String ssrDrAutograph;//医生签名
    private String ssrSignId;//签约单主键
    private String ssrEmergencyContactName;//紧急联系人
    private String ssrEmergencyContactTel;//紧急联系人电话
    private String ssrDrId;//医生主键

    private String patientSex;//性别
    private String patientIdno;//居民身份证
    private String patientBirthDay;//出生日期
    private String patientAddr;//地址
    private String patientTel;//电话
    private String fwValue;//服务类型值
    private String fwTitle;//服务类型名称
    private String jjValue;//经济类型值
    private String jjTitle;//经济类型名称
    private String signFromDate;//签约开始时间
    private String signToDate;//签约到期时间
    private String signDrName;//团队医生姓名
    private String signDrTel;//团队医生电话
    private String signDrAssistantId;//助理医生主键
    private String signDrAssistantName;//助理医生姓名
    private String signDrAssistantTel;//助理医生电话

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSsrPatientId() {
        return ssrPatientId;
    }

    public void setSsrPatientId(String ssrPatientId) {
        this.ssrPatientId = ssrPatientId;
    }

    public String getSsrPatientName() {
        return ssrPatientName;
    }

    public void setSsrPatientName(String ssrPatientName) {
        this.ssrPatientName = ssrPatientName;
    }

    public String getSsrServeTime() {
        return ssrServeTime;
    }

    public void setSsrServeTime(Timestamp ssrServeTime) {
        if(ssrServeTime != null){
            this.ssrServeTime = ExtendDate.getYMD_h_m_s(ssrServeTime);
        }else{
            this.ssrServeTime = "";
        }
    }

    public String getSsrServeAddr() {
        return ssrServeAddr;
    }

    public void setSsrServeAddr(String ssrServeAddr) throws Exception {
        if(StringUtils.isNotBlank(ssrServeAddr)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode cdCode = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SERVICEADDR,ssrServeAddr);
            if(cdCode != null){
                ssrServeAddr = cdCode.getCodeTitle();
            }
        }
        this.ssrServeAddr = ssrServeAddr;
    }

    public Integer getSsrHP() {
        return ssrHP;
    }

    public void setSsrHP(Integer ssrHP) {
        this.ssrHP = ssrHP;
    }

    public Integer getSsrLP() {
        return ssrLP;
    }

    public void setSsrLP(Integer ssrLP) {
        this.ssrLP = ssrLP;
    }

    public String getSsrBloodSugar() {
        return ssrBloodSugar;
    }

    public void setSsrBloodSugar(String ssrBloodSugar) {
        this.ssrBloodSugar = ssrBloodSugar;
    }

    public String getSsrContentValue() {
        return ssrContentValue;
    }

    public void setSsrContentValue(String ssrContentValue) throws Exception {
        if(StringUtils.isNotBlank(ssrContentValue)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode cdCode = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SERVICECONTENT,ssrContentValue);
            if(cdCode != null){
                ssrContentValue = cdCode.getCodeTitle();
            }
        }
        this.ssrContentValue = ssrContentValue;
    }

    public String getSsrMainSituation() {
        return ssrMainSituation;
    }

    public void setSsrMainSituation(String ssrMainSituation) {
        this.ssrMainSituation = ssrMainSituation;
    }

    public String getSsrHealthGuidance() {
        return ssrHealthGuidance;
    }

    public void setSsrHealthGuidance(String ssrHealthGuidance) {
        this.ssrHealthGuidance = ssrHealthGuidance;
    }

    public String getSsrPatientAutograph() {
        return ssrPatientAutograph;
    }

    public void setSsrPatientAutograph(String ssrPatientAutograph) {
        this.ssrPatientAutograph = ssrPatientAutograph;
    }

    public String getSsrPatientWithin() {
        return ssrPatientWithin;
    }

    public void setSsrPatientWithin(String ssrPatientWithin) {
        this.ssrPatientWithin = ssrPatientWithin;
    }

    public String getSsrDrAutograph() {
        return ssrDrAutograph;
    }

    public void setSsrDrAutograph(String ssrDrAutograph) {
        this.ssrDrAutograph = ssrDrAutograph;
    }

    public String getSsrSignId() {
        return ssrSignId;
    }

    public void setSsrSignId(String ssrSignId) {
        this.ssrSignId = ssrSignId;
    }

    public String getSsrEmergencyContactName() {
        return ssrEmergencyContactName;
    }

    public void setSsrEmergencyContactName(String ssrEmergencyContactName) {
        this.ssrEmergencyContactName = ssrEmergencyContactName;
    }

    public String getSsrEmergencyContactTel() {
        return ssrEmergencyContactTel;
    }

    public void setSsrEmergencyContactTel(String ssrEmergencyContactTel) {
        this.ssrEmergencyContactTel = ssrEmergencyContactTel;
    }

    public String getSsrDrId() {
        return ssrDrId;
    }

    public void setSsrDrId(String ssrDrId) {
        this.ssrDrId = ssrDrId;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientBirthDay() {
        return patientBirthDay;
    }

    public void setPatientBirthDay(String patientBirthDay) {
        this.patientBirthDay = patientBirthDay;
    }

    public String getPatientAddr() {
        return patientAddr;
    }

    public void setPatientAddr(String patientAddr) {
        this.patientAddr = patientAddr;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getFwValue() {
        return fwValue;
    }

    public void setFwValue(String fwValue) {
        this.fwValue = fwValue;
    }

    public String getFwTitle() {
        return fwTitle;
    }

    public void setFwTitle(String fwTitle) {
        this.fwTitle = fwTitle;
    }

    public String getJjValue() {
        return jjValue;
    }

    public void setJjValue(String jjValue) {
        this.jjValue = jjValue;
    }

    public String getJjTitle() {
        return jjTitle;
    }

    public void setJjTitle(String jjTitle) {
        this.jjTitle = jjTitle;
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(String signFromDate) {
//        if(signFromDate != null){
//            this.signFromDate = ExtendDate.getChineseYMD(signFromDate);
//        }else{
//            this.signFromDate = "";
//        }
        this.signFromDate = signFromDate;
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(String signToDate) {
//        if(signToDate != null){
//            this.signToDate = ExtendDate.getChineseYMD(signToDate);
//        }else{
//            this.signToDate = "";
//        }
        this.signToDate = signToDate;
    }

    public String getSignDrName() {
        return signDrName;
    }

    public void setSignDrName(String signDrName) {
        this.signDrName = signDrName;
    }

    public String getSignDrTel() {
        return signDrTel;
    }

    public void setSignDrTel(String signDrTel) {
        this.signDrTel = signDrTel;
    }

    public String getSignDrAssistantId() {
        return signDrAssistantId;
    }

    public void setSignDrAssistantId(String signDrAssistantId) {
        this.signDrAssistantId = signDrAssistantId;
    }

    public String getSignDrAssistantName() {
        return signDrAssistantName;
    }

    public void setSignDrAssistantName(String signDrAssistantName) {
        this.signDrAssistantName = signDrAssistantName;
    }

    public String getSignDrAssistantTel() {
        return signDrAssistantTel;
    }

    public void setSignDrAssistantTel(String signDrAssistantTel) {
        this.signDrAssistantTel = signDrAssistantTel;
    }
}
